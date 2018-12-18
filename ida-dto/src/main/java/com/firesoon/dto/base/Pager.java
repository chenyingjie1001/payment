package com.firesoon.dto.base;

import lombok.Data;

/**
 * @author create by yingjie.chen on 2018/10/29.
 * @version 2018/10/29 16:01
 */
@Data
public class Pager {
    private int pageNum = 1;
    private int pageSize = 10;
}
