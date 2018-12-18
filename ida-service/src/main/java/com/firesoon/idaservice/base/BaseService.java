package com.firesoon.idaservice.base;

import java.util.List;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 18:16
 */
public interface BaseService<T> {
    List<T> find(T t);


    void add(T t);

    void del(T t);

    int update(T t);
}
