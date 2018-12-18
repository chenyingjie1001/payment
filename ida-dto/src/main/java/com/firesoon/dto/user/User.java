package com.firesoon.dto.user;

import com.firesoon.dto.base.Pager;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2017/10/11.
 * @version 2017/10/11 14:40
 */

@Data
public class User extends Pager implements Serializable {

    private static final long serialVersionUID = 5432699710222882875L;
    private Integer id;
    private String login_name;
    private String user_name;
    private String password;
    private String state;
    private String create_time;
    private String mobile_phone;
    private String email;
    private String nick_name;
    private String position;
    private String birthday;
    private String address;
    private Integer dept_id;
    private String jianpin;
    private Integer emp_id;
    private Integer user_style;
    private String user_organ;
    private Integer login_status;

    /**
     * 配置科室权限
     */
    private Map<String, List<String>> dept;
    private Map<String, List<String>> masterDept;
}
