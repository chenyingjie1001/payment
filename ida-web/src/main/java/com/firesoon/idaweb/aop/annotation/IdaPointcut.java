package com.firesoon.idaweb.aop.annotation;

import java.lang.annotation.*;

/**
 * @author create by yingjie.chen on 2018/5/31.
 * @version 2018/5/31 14:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IdaPointcut {

    String value() default "";
}
