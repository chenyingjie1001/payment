package com.firesoon.idaservice.common.impl;

import com.firesoon.idaservice.common.CommonService;
import com.firesoon.paymentmapper.common.CommonMapper;
import com.firesoon.paymentmapper.importRecord.ImportRecordMapper;
import com.firesoon.utils.PropertiesUtil;
import com.firesoon.utils.StringUtil;
import com.firesoon.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:57
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private ImportRecordMapper importRecordMapper;


    @Override
    public Integer importTimes(Map<String, Object> params) {
        return importRecordMapper.findImportRecord(params).size();
    }

    @Override
    @Transactional
    public Integer importXls(Map<String, Object> params) {
        //成功条数
        int total = 0;
        //成功条数
        if (params.get("bak") == null) {
            params.put("bak", "");
        }
        insertOrUpdateBill(params);
        commonMapper.insertBillRecord(params);
        String filename = PropertiesUtil.properties.getProperty("xlsfilePath") + params.get("filename");
        String month = params.get("month").toString();
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(new File(filename), Map.class, importParams);
        //查找excle配置
        String sql = "select name \"name\", type \"type\", fieldname \"fieldname\", ISKEY \"key\" from ida_xls_view";
        log.debug("查找excle配置: " + sql);
        List<Map<String, Object>> xlsViews = jdbcTemplate.queryForList(sql);
        //第一步 先判断这个xls表有没建 这个表名是固定的
        sql = "select count(1) \"number\" from user_tables where table_name = 'IDA_CHARGEBILL_DETAIL'";
        log.debug("判断表是否存在: " + sql);
        Map<String, Object> number = jdbcTemplate.queryForMap(sql);
        if (Integer.parseInt(number.get("number").toString()) > 0) {
            //表已经存在 不需要创建

        } else {
            createTable(xlsViews);
        }
        //第二步判断是否有数据
        // 确定明细的主键 先定死，后续可以在做成配置
        List<Map<String, Object>> keys = new ArrayList<>();
        for (Map<String, Object> xlsView : xlsViews) {
            if ("1".equals(xlsView.get("key"))) {
                //是主键
                Map<String, Object> map = new HashMap<>();
                map.put("name", xlsView.get("name"));
                map.put("fieldname", xlsView.get("fieldname"));
                keys.add(map);
            }
        }
//        String key = "审核意见书编码";
        String chargeMoney = "扣除金额";
        String feedbackComplaint = "反馈申诉";
        sql = "select count(1) \"count\" from ida_chargebill_detail t where t.month= '" + month + "'";
        log.debug("判断是否有当月数据: " + sql);
        Map<String, Object> count = jdbcTemplate.queryForMap(sql);
        List<Map<String, Object>> newList = list.stream().filter(t -> !"合计：".equals(t.get("序号"))).collect(Collectors.toList());
        if (Integer.parseInt(count.get("count").toString()) > 0) {
            //当月的数据已经存在
            //先把所有数据isnew改成0 , 申请成功标记改为成功1
            String updateNewAndAppeal = "update IDA_CHARGEBILL_DETAIL t set t.isnew = '0', " +
                    "t.isappeal = '1', " +
                    "t.updatedate=sysdate, " +
                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                    "where t.month = '" + month + "'";
            log.debug("先把所有数据isnew改成0 , 申请成功标记改为成功1：" + updateNewAndAppeal);
            jdbcTemplate.execute(updateNewAndAppeal);
            //判断有无新增的 标记为新增
            //判断有误减少的 标记为申请成功
            //判断是否扣除金额为0 标记为申请成功
            for (Map<String, Object> data : newList) {
                //判断唯一条件的sql
                StringBuilder wsql = new StringBuilder();
                wsql.append(" 1=1 ");
                for (Map<String, Object> key : keys) {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("name"))) + "' ");
                }
                String charge_money = StringUtil.removeCurrency(StringUtil.null2Str(data.get(chargeMoney)));
                String feedback_complaint = StringUtil.null2Str(data.get(feedbackComplaint));

                String checksql = "select count(1) \"count\" from IDA_CHARGEBILL_DETAIL t where " + wsql.toString() + " and t.month = '" + month + "'";
                log.debug("判断该条数据是否存在：" + checksql);
                Map<String, Object> countMap = jdbcTemplate.queryForMap(checksql);
                if (Integer.parseInt(countMap.get("count").toString()) > 0) {
                    //存在
                    if (!StringUtils.isBlank(charge_money) && !"0".equals(charge_money)) {// 申请未成功标记改成0
                        //申请成功标记更新为0
                        //填写了申诉理由，但是不成功的即为失败
                        String updateAppeal = "update IDA_CHARGEBILL_DETAIL t set t.isappeal = '0', " +
                                "t.feedback_complaint = '" + feedback_complaint + "' " +
                                "t.updatedate=sysdate, " +
                                "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                "where " + wsql.toString() +
                                "and t.month = '" + month + "'";
                        if (!StringUtils.isBlank(feedback_complaint.trim())) {
                            updateAppeal = "update IDA_CHARGEBILL_DETAIL t set t.isappeal = '-1', " +
                                    "t.feedback_complaint = '" + feedback_complaint + "' " +
                                    "t.updatedate=sysdate, " +
                                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                    "where " + wsql.toString() +
                                    "and t.month = '" + month + "'";
                        }
                        log.debug("申请未成功标记改成0: " + updateAppeal);
                        jdbcTemplate.execute(updateAppeal);
                    } else if (!StringUtils.isBlank(charge_money) && "0".equals(charge_money)) {
                        //等于0
                        String updateMoney = "update IDA_CHARGEBILL_DETAIL t set t.charge_money = '0', " +
                                "t.feedback_complaint = '" + feedback_complaint + "' " +
                                "t.updatedate=sysdate, " +
                                "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                "where " + wsql.toString() +
                                "and t.month = '" + month + "'";
                        log.debug("申请成功更改扣除金额，状态不需要改，批量的时候已经更新：" + updateMoney);
                        jdbcTemplate.execute(updateMoney);
                    }
                } else {
                    //不存在
                    insert2db(xlsViews, month, data, "1");
                    total++;
                }

            }

        } else {
            //不存在
            for (Map<String, Object> data : newList) {
                insert2db(xlsViews, month, data, "0");
                total++;
            }
            log.info("新增语句执行结束");
        }
        // 调用存储过程model.P_getDept('2018-08');
        Thread thread = new Thread(() -> {
            jdbcTemplate.execute("call ida.P_cory_test()");
            jdbcTemplate.execute("call model.P_getDept('" + month + "')");
        });
        thread.start();
        return total;
    }


    private void insertOrUpdateBill(Map<String, Object> params) {
        String sql = "select count(1) \"time\" from ida_charge_bill where month = '" + params.get("month") + "'";
        Map<String, Object> time = jdbcTemplate.queryForMap(sql);

        if (Integer.parseInt(time.get("time").toString()) > 0) {
            //update
            commonMapper.updateBill(params);
        } else {
            //insert
            commonMapper.insertBill(params);
        }
    }

    /**
     * 私有方法创建需要的表
     *
     * @param xlsViews
     */
    private void createTable(List<Map<String, Object>> xlsViews) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table IDA_CHARGEBILL_DETAIL(ID VARCHAR2(64), month VARCHAR2(64), ");
        xlsViews.forEach(xls -> sb.append(xls.get("fieldname") + "  " + xls.get("type") + "(512), "));
        sb.append("deducteproject varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("deductedept varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("iswrite char(1), "); //是否填写   判断条件都是1是0否
        sb.append("appealstr varchar2(512), "); //申诉理由
        sb.append("appealimg clob, "); //申诉图片
        sb.append("appealdate date, "); //申诉时间
        sb.append("appealuser varchar2(32), "); //申诉人
        sb.append("uname varchar2(64), "); //图片name
        sb.append("isnew char(1), "); //是否新添加
        sb.append("isappeal varchar2(2), "); //是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败
        sb.append("createdate date, ");
        sb.append("updatedate date, ");
        sb.append("createby varchar2(32), ");
        sb.append("updateby varchar2(32) ");
        sb.append(")");
        log.debug("建表语句: " + sb.toString());
        jdbcTemplate.execute(sb.toString());
        xlsViews.forEach(xls -> jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL." + xls.get("fieldname") + " is '" + xls.get("name") + "'"));
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.deducteproject is '扣款核算项目  需要数据开发去匹配'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.iswrite is '是否填写   判断条件都是1是0否'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.appealstr is '申诉理由'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.appealimg is '申诉图片'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.uname is '图片name'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.isnew is '是否新添加'");
        jdbcTemplate.execute("comment on column IDA_CHARGEBILL_DETAIL.isappeal is '是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败'");
        log.debug("建表语句执行结束");
    }

    /**
     * 私有方法 插入数据到db
     *
     * @param xlsViews
     * @param month
     * @param data
     * @param isnew
     */
    private void insert2db(List<Map<String, Object>> xlsViews, String month, Map<String, Object> data, String isnew) {
        StringBuilder insertsql = new StringBuilder();
        insertsql.append("insert into IDA_CHARGEBILL_DETAIL (id, month, ");
        xlsViews.forEach(xls -> insertsql.append(xls.get("fieldname") + ", "));
        insertsql.append("deducteproject, ");
        insertsql.append("deductedept, ");
        insertsql.append("iswrite, "); //是否填写   判断条件都是1是0否
        insertsql.append("appealstr, "); //申诉理由
        insertsql.append("appealimg, "); //申诉图片
        insertsql.append("appealdate, "); //申诉图片
        insertsql.append("appealuser, "); //申诉图片
        insertsql.append("uname, "); //图片name
        insertsql.append("isnew, "); //是否新添加
        insertsql.append("isappeal, "); //是否申诉
        insertsql.append("createdate, ");
        insertsql.append("updatedate, ");
        insertsql.append("createby, ");
        insertsql.append("updateby ");
        insertsql.append(")");
        insertsql.append("values(");
        insertsql.append("sys_guid(), '" + month + "', ");
        xlsViews.forEach(xls -> {
//            if (xls.get("fieldname").equals("department_name")) { //如果是科室
////                insertsql.append("getkeshi('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//                insertsql.append("'正在匹配...', ");
//            }
            //这个项目名称要当主键 所以必须不能更改
//            else if (xls.get("fieldname").equals("item_name")) { //如果是项目
//                insertsql.append("getprojectname('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//            }
//            else {
            insertsql.append("'" + StringUtil.removeCurrency(StringUtil.null2Str(data.get(xls.get("name")))) + "', ");
//            }
        });
        insertsql.append("'正在匹配...', '正在匹配...', '0', null, null, null, null, null, '" + isnew + "', '0', sysdate, null, '" + UserUtil.getUser().getUser_name() + "', null)");
        log.debug("新增语句：" + insertsql.toString());
        jdbcTemplate.execute(insertsql.toString());
    }
}
