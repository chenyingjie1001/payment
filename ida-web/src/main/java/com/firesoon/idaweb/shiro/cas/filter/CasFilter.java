package com.firesoon.idaweb.shiro.cas.filter;

import com.alibaba.fastjson.JSONObject;
import com.firesoon.dto.base.ResultMessage;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author create by yingjie.chen on 2018/1/8.
 * @version 2018/1/8 14:34
 */
public class CasFilter extends AuthenticatingFilter {

    private static Logger logger = LoggerFactory.getLogger(CasFilter.class);

    // the name of the parameter service ticket in url
    private static final String TICKET_PARAMETER = "ticket";

    // the url where the application is redirected if the CAS service ticket validation failed (example : /mycontextpatch/cas_error.jsp)
    private String failureUrl;


    /**
     * The token created for this authentication is a CasToken containing the CAS service ticket received on the CAS service url (on which
     * the filter must be configured).
     *
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;//httpRequest.getRequestURI()
        String ticket = httpRequest.getParameter(TICKET_PARAMETER);
        return new CasToken(ticket);
    }

    /**
     * Execute login by creating {@link #createToken(ServletRequest, ServletResponse) token} and logging subject
     * with this token.
     *
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    /**
     * Returns <code>false</code> to always force authentication (user is never considered authenticated by this filter).
     *
     * @param request the incoming request
     * @param response the outgoing response
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return <code>false</code>
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * If login has been successful, redirect user to the original protected url.
     *
     * @param token the token representing the current authentication
     * @param subject the current authenticated subjet
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
//        issueSuccessRedirect(request, response);
        saveRequestAndRedirectToLogin(request, response,true);
        return false;
    }

    /**
     * If login has failed, redirect user to the CAS error page (no ticket or ticket validation failed) except if the user is already
     * authenticated, in which case redirect to the default success url.
     *
     * @param token the token representing the current authentication
     * @param ae the current authentication exception
     * @param request the incoming request
     * @param response the outgoing response
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        // is user authenticated or in remember me mode ?
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
//                issueSuccessRedirect(request, response);
                saveRequestAndRedirectToLogin(request, response, true);
            } catch (Exception e) {
                logger.error("Cannot redirect to the default success url", e);
            }
        } else {
            try {
                saveRequestAndRedirectToLogin(request, response,false);
            } catch (IOException e) {
                logger.error("Cannot redirect to failure url : {}", failureUrl, e);
            }
        }
        return false;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response, boolean loginFlag) throws IOException {
        WebUtils.saveRequest(request);
        if(loginFlag) {
            WebUtils.issueRedirect(request, response,getSuccessUrl());
        }else {
            ResultMessage<String> rm = new ResultMessage<String>();
            rm.setMsg("未登录");
            rm.setHttpCode(401);
            rm.setUrl(getLoginUrl());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONObject.toJSONString(rm));
            String url = ((HttpServletRequest)request).getRequestURI();
            logger.info("访问接口=======" + url);
            response.setContentType("text/json; charset=UTF-8");
            logger.info(getLoginUrl());
        }

    }
}
