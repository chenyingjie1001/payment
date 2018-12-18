package com.firesoon.idaservice.user;


import com.firesoon.dto.user.User;

import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 18:16
 */
public interface UserService {
    List<User> findUser(User user);

    Map<String, Object> findVersion();

    User findUserByLogin(String loginname);

    /**
     * v6.0.1改版 显示主科室
     * @param user
     * @return
     */
    Map<String, Object> userDepts(User user);

    void setDept(Map<String, Object> map, User user);
}
