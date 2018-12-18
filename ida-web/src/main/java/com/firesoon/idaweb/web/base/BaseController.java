package com.firesoon.idaweb.web.base;

import com.firesoon.dto.base.MyExceptionResponse;
import com.firesoon.dto.base.ReMsg;
import com.firesoon.dto.base.WarnException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@ControllerAdvice
public class BaseController {


    /**
     * 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
     */
    @ExceptionHandler
    @ResponseBody
    public MyExceptionResponse exceptionHandler(Exception e,
                                                HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        MyExceptionResponse resp = new MyExceptionResponse();
        resp.setTimestamp(new Date());
        resp.setMsg("发生错误"+e.getMessage());
        if (e instanceof WarnException) {
            resp.setHttpCode(200);
        } else if (e instanceof RuntimeException) {
            resp.setHttpCode(300);
        } else {
            resp.setHttpCode(500);
        }
        return resp;
    }
    /**
     * @param o
     * @return
     */
    public Object succ(Object... o) {
        ReMsg rm = new ReMsg();
        rm.setHttpCode(200);
        rm.setMsg("请求成功");
        if(o != null && o.length == 1){
            rm.setData(o[0]);
        }else{
            rm.setData(o);
        }
        return rm;
    }

    public Object failure(Object... o) {
        ReMsg rm = new ReMsg();
        rm.setHttpCode(500);
        rm.setMsg("请求失败");
        if(o != null && o.length == 1){
            rm.setData(o[0]);
        }else{
            rm.setData(o);
        }
        return rm;
    }

    protected boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0;
    }
}
