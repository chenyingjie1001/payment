package com.firesoon.idaweb.web.user;

import com.firesoon.idaservice.user.UserService;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

/**
 * @author create by yingjie.chen on 2018/11/27.
 * @version 2018/11/27 10:05
 */

@Api
@Slf4j
@RestController
@RequestMapping(method = RequestMethod.POST, value = "/api/user/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "getUser", notes = "getUser")
    @GetMapping(value = "/user")
    public Object getUser() {
        Map<String, Object> result = new HashMap<>();
        result.put("user", UserUtil.getUser());
        result.putAll(userService.findVersion());
        return succ(result);
    }
}
