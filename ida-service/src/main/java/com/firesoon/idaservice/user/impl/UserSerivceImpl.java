package com.firesoon.idaservice.user.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.firesoon.dto.user.User;
import com.firesoon.idaservice.user.UserService;
import com.firesoon.paymentmapper.user.UserMapper;
import com.firesoon.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 18:18
 */
@Service
public class UserSerivceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> findUser(User user) {
        return mapper.find(user);
    }

    @Override
    public User findUserByLogin(String loginname) {
        return mapper.findUserByLogin(loginname);
    }

    @Override
    public Map<String, Object> findVersion() {
        return mapper.findApplicationVersion();
    }

    @Override
    public Map<String, Object> userDepts(User user) {
        Map<String, Object> result = new HashMap<String, Object>();
//        List<Map<String, Object>> bussessDepts = null;
        List<Map<String, Object>> masterDepts = null;
        //获取id5
        Map<String, Object> dataPer = mapper.dataPermisResources();
        //id 5 和userid 一起当参数 获取用户数据权限
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", user.getId());
        params.put("datapermisresid", dataPer.get("ID"));
        List<Map<String, Object>> userIds = mapper.userDataPermissions(params);
        List<Map<String, Object>> deptIds = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : userIds) {
            String permissions = map.get("PERMISSIONS") + "";
            List<Map<String, Object>> pers = (List<Map<String, Object>>) JSONUtils.parse(permissions);
            //数据格式[{column=id, data=316}] 我们需要的参数是[{id:316}]
            for (Map<String, Object> per : pers) {
                //创建一个参数map合成数据生成参数条件Map
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("id", per.get("data"));
                deptIds.add(paramMap);
            }
        }
        if (deptIds.size() == 0) {
            result.put("result", "0"); //该用户没有数据权限
            result.put("list", masterDepts);
            return result;
        }
        //根据数据全新获取用户的主科室数据
        masterDepts = mapper.masterDept(deptIds);
        if (masterDepts.size() == 0) {
            result.put("result", "1"); //该用户没有科室权限
            result.put("list", masterDepts);
            return result;
        }
        boolean isAdministration = false; //是否行政科室
        for (Map<String, Object> masterDept : masterDepts) {
            Object attribute = masterDept.get("ATTRIBUTE");
            if (attribute != null && Integer.parseInt(attribute.toString()) == 1) {//==1 是行政科室
                isAdministration = true;
                break;
            }
        }
//        bussessDepts = mapper.bussessDept(deptIds);
        if (isAdministration) {
            result.put("result", "2"); //该用户是行政科室
            result.put("list", masterDepts);
            return result;
        }
        //根据主科室获取用户业务科室

        result.put("result", "3"); //该用户是业务科室
        result.put("list", masterDepts);
        return result;
    }

    /**
     * result.put("result", "0"); //该用户没有数据权限
     * result.put("result", "1"); //该用户没有科室权限
     * result.put("result", "2"); //该用户是行政科室
     * result.put("result", "3"); //该用户是业务科室
     *
     * @param map userSerivce.userDepts的返回集 {"result": "3", "list": ["DEPTCODE": "KSh-007","DEPTNAME": "心内科"]}
     */
    @Override
    public void setDept(Map<String, Object> map, User user) {
        String result = map.get("result").toString();
        Object list = map.get("list");
        List<Map<String, Object>> temps = list != null ? (List) list : new ArrayList<>();
        Map<String, List<String>> dataMap = new HashMap<>();
        List<String> depts = new ArrayList<>();
        Map<String, List<String>> masterDataMap = new HashMap<>();
        List<String> masterDepts = new ArrayList<>();
        if ("2".equals(result)) {
            masterDataMap.put("科室", null); //行政科室，直接写null
            dataMap.put("科室", null); //行政科室，直接写null
        } else if ("3".equals(result)) {
            if (temps.size() > 0) {
                for (Map<String, Object> t : temps) {
                    masterDepts.add(t.get("DEPTNAME") + "");
                }
                masterDataMap.put("科室", masterDepts);
                List<Map<String, Object>> bussessDepts = mapper.bussessDept(temps);
                for (Map<String, Object> temp : bussessDepts) {
                    depts.add(temp.get("DEPTNAME") + "");
                }
                if (depts.size() > 0) {
                    dataMap.put("科室", depts);
                } else {
                    depts.add("该用户没有科室");
                    dataMap.put("科室", depts);
                }
            } else {
                masterDepts.add("该用户没有科室");
                masterDataMap.put("科室", masterDepts);
                depts.add("该用户没有科室");
                dataMap.put("科室", depts);
            }
        } else {
            masterDepts.add("该用户没有科室");
            masterDataMap.put("科室", masterDepts);
            depts.add("该用户没有科室");
            dataMap.put("科室", depts);
        }
        user.setDept(dataMap);
        user.setMasterDept(masterDataMap);
        UserUtil.setUser(user);
    }
}
