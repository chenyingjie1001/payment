package com.firesoon.idaweb.aop;

import com.firesoon.dto.user.User;
import com.firesoon.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 使用@Aspect注解将一个java类定义为切面类
 * 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
 * 根据需要在切入点不同位置的切入内容，5种类型的通知
 * 使用@Before在切入点开始处切入内容
 * 使用@After在切入点结尾处切入内容
 * 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
 * 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
 * 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
 *
 * @author create by yingjie.chen on 2018/5/31.
 * @version 2018/5/31 14:06
 */
@Slf4j
@Aspect
@Component
public class WebAspect {

    @Pointcut("@within(com.firesoon.idaweb.aop.annotation.IdaPointcut) || @annotation(com.firesoon.idaweb.aop.annotation.IdaPointcut)")
    public void webController() {

    }

    /**
     * @param pjp 在mapperxml中加入
     *            <if test="科室 != null and 科室.size > 0">
     *            AND department_name IN
     *            <foreach item="deptName" index="index" collection="科室" open="(" separator="," close=")">
     *            #{deptName}
     *            </foreach>
     *            </if>
     *            即可
     * @return
     * @throws Throwable
     */
    @Around("webController()")
    public Object around(ProceedingJoinPoint pjp) {
        log.info("正在进行数据权限，科室过滤");
        User user = UserUtil.getUser();
        Object retVal = null;
        try {
            Object[] args = pjp.getArgs();
            for (Object arg : args) {
                if (arg instanceof Map) {
                    Map map = (Map) arg;
                    map.put("username", user.getUser_name());
                    map.putAll(user.getDept());
                }
            }
            retVal = pjp.proceed(args);
        } catch (Throwable e) {
            log.info(e.getMessage());
        }
        return retVal;
    }


}
