package com.firesoon.idaweb.shiro.cas.filter;

import com.alibaba.fastjson.JSONObject;
import com.firesoon.dto.base.ResultMessage;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wenyuan.guan
 * @version 创建时间：2018年1月4日 下午2:55:03
 * 类说明
 */
public class SkyLogoutFilter extends LogoutFilter {

    private static Logger logger = LoggerFactory.getLogger(SkyLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        String redirectUrl = getRedirectUrl(request, response, subject);
        //try/catch added for SHIRO-298:
        logger.info("redirectUrl=======" + redirectUrl);
        try {
            subject.logout();
        } catch (SessionException ise) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }

        ResultMessage<String> rm = new ResultMessage<String>();
        rm.setMsg("未登录");
        rm.setHttpCode(401);
        rm.setUrl(redirectUrl);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONObject.toJSONString(rm));
        String url = ((HttpServletRequest) request).getRequestURI();
        logger.info("访问接口=======" + url);
        response.setContentType("text/json; charset=UTF-8");
        logger.info(redirectUrl);
        return false;
    }
}
