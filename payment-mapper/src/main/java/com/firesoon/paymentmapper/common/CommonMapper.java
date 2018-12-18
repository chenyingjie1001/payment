package com.firesoon.paymentmapper.common;

import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.List;

/**
 * @author create by yingjie.chen on 2018/11/23.
 * @version 2018/11/23 16:53
 */
@Mapper
public interface CommonMapper extends BaseMapper {


    Integer updateBill(Map<String, Object> param);

    Integer insertBill(Map<String, Object> param);

    Integer insertBillRecord(Map<String, Object> param);


    /**
     * 批量插入list的数据
     * @param list
     * @return
     */
    Integer insertBillDetail(List<Map<String, Object>> list);
}
