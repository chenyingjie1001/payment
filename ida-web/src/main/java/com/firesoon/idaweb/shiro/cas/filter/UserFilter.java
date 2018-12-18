package com.firesoon.idaweb.shiro.cas.filter;

import com.alibaba.fastjson.JSONObject;
import com.firesoon.dto.base.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/** 
 * @author wenyuan.guan
 * @version 创建时间：2017年12月27日 下午3:15:36 
 * 类说明 
 */
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {

	private static Logger logger = LoggerFactory.getLogger(UserFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// TODO Auto-generated method stub
		return super.isAccessAllowed(request, response, mappedValue);
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
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
		return false;
	}
}
