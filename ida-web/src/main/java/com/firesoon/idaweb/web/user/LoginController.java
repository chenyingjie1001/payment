package com.firesoon.idaweb.web.user;

import com.firesoon.idaweb.shiro.cas.ShiroCasConfiguration;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.dto.base.ResultMessage;
import com.firesoon.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author create by yingjie.chen on 2018/11/19.
 * @version 2018/11/19 11:26
 */
@Api
@Slf4j
@RestController
@RequestMapping(method = RequestMethod.POST)
public class LoginController extends BaseController {

    @ApiOperation(value = "logout", notes = "logOut")
    @GetMapping(value = "/logout")
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        subject.getSession().removeAttribute("user");
//        return success();
        String redirectUrl = ShiroCasConfiguration.casLogoutUrl + "?service=" + ShiroCasConfiguration.successUrl;
        ResultMessage<String> rm = new ResultMessage<String>();
        rm.setMsg("退出登录");
        rm.setHttpCode(401);
        rm.setUrl(redirectUrl);
        return rm;
    }
}
