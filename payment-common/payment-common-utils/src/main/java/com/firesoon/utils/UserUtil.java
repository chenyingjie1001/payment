package com.firesoon.utils;

import com.firesoon.dto.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 18:21
 */
@Slf4j
public class UserUtil {

    public static User getUserMsg() {
        Object obj = SecurityUtils.getSubject().getPrincipal();
        return (User) obj;
    }

    public static User getUser() {
        Session session = SecurityUtils.getSubject().getSession();
        return (User) session.getAttribute("user");
    }

    public static void setUser(User user) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
    }

    /**
     * result.put("result", "0"); //该用户没有数据权限
     * result.put("result", "1"); //该用户没有科室权限
     * result.put("result", "2"); //该用户是行政科室
     * result.put("result", "3"); //该用户是业务科室
     *
     * @param map userSerivce.userDepts的返回集 {"result": "3", "list": ["DEPTCODE": "KSh-007","DEPTNAME": "心内科"]}
     */
    public static void setDept(Map<String, Object> map, User user) {
        String result = map.get("result").toString();
        Object list = map.get("list");
        List<Map<String, Object>> temps = list != null ? (List) list : new ArrayList<Map<String, Object>>();
        Map<String, List<String>> dataMap = new HashMap<String, List<String>>();
        List<String> depts = new ArrayList<String>();
        if ("2".equals(result)) {
            dataMap.put("科室", null); //行政科室，直接写null
        } else if ("3".equals(result)) {
            if (temps.size() > 0) {
                for (Map<String, Object> temp : temps) {
                    depts.add(temp.get("DEPTNAME") + "");
                    dataMap.put("科室", depts);
                    break;
                }
            } else {
                depts.add("该用户没有科室");
                dataMap.put("科室", depts);
            }
        } else {
            depts.add("该用户没有科室");
            dataMap.put("科室", depts);
        }
        user.setDept(dataMap);
        setUser(user);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
