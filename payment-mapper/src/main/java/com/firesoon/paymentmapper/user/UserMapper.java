package com.firesoon.paymentmapper.user;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 17:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUserByLogin(String loginname);


    Map<String, Object> findApplicationVersion();

    /**
     * portal.base_datapermisresources
     * 查找到id 5
     * @return
     */
    Map<String, Object> dataPermisResources();

    /**
     * portal.base_userdatapermissions
     * 查找到对应的科室id数据
     * @param map
     * @return
     */
    List<Map<String, Object>> userDataPermissions(Map<String, Object> map);

    /**
     * 查找对应的科室信息
     * @param list
     * @return
     */
    List<Map<String, Object>> masterDept(List<Map<String, Object>> list);

    /**
     * 如果不是行政科室，就要查询对应的业务科室
     * @param list
     * @return
     */
    List<Map<String, Object>> bussessDept(List<Map<String, Object>> list);
}
