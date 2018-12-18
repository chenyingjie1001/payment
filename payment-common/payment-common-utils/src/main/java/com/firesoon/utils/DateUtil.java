package com.firesoon.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author create by yingjie.chen on 2018/10/12.
 * @version 2018/10/12 14:33
 */
public class DateUtil {

    private final static SimpleDateFormat STARTFORMAT = new SimpleDateFormat("yyyyMMdd000000");
    private final static SimpleDateFormat ENDFORMAT = new SimpleDateFormat("yyyyMMdd235959");
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat _FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat FORMATSEVENTEEN = new SimpleDateFormat("yyyyMMddHHmmssSSS");


    public static String getCurrentStartDateFormat() {
        return STARTFORMAT.format(new Date());
    }
    public static String getCurrentEndDateFormat() {
        return ENDFORMAT.format(new Date());
    }
    public static String getCurrentDate() {
        return FORMAT.format(new Date());
    }
    public static String _getCurrentDate() {
        return _FORMAT.format(new Date());
    }
    public static String getCurrentDateFORMATSEVENTEEN() {
        return FORMATSEVENTEEN.format(new Date());
    }
    public static String getYesDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        return FORMAT.format(cal.getTime());
    }
}
