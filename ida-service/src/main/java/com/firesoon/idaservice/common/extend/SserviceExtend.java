package com.firesoon.idaservice.common.extend;

import com.firesoon.idaservice.common.Sservice;

/**
 * @author create by yingjie.chen on 2018/12/17.
 * @version 2018/12/17 17:47
 */
public class SserviceExtend extends Sservice {


    @Override
    public void see() {
        System.out.println("i am see");
    }

    public static void main(String[] args) {
        SserviceExtend ss = new SserviceExtend();
        ss.see();
        ss.say();
    }
}
