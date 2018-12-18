package com.firesoon.dto.base;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/19.
 * @version 2018/11/19 10:31
 */
@Data
public class ResultMessage<T> {

    private List<T> data;
    private int httpCode = 200;
    private String msg = "Success";
    private List<Map<String, Object>> mapdata;
    private T t;
    private Map<String, Object> m;
    private Date timestamp;
    private Object col;
    private Object tag;
    private String url;
}
