package com.firesoon.idaservice.common;

import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:56
 */
public interface CommonService {


    /**
     * 申诉成功的判断依据：扣除金额＝0（绍兴）；两条相同的扣款记录，扣除金额正负相抵（宁波）；
     * 扣款单的总池子－导入的扣款单若>0，则扣款单的总池子－导入的扣款的扣款为申诉成功状态（判断的字段需要程序上根据不同医院可变）
     * 申诉失败的判断依据：填写了申诉理由，但是不成功的即为失败
     * 不申诉的判断依据：除了成功与失败的，剩下的即为不申诉
     * @param params
     * @return
     */
    Integer importXls(Map<String, Object> params);



    Integer importTimes(Map<String, Object> params);
}
