package com.firesoon.utils;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

/**
 * @author create by yingjie.chen on 2018/11/23.
 * @version 2018/11/23 16:05
 */
public class StringUtil {

    /**
     * change null to String
     * @param object
     * @return
     */
    public static String null2Str(Object object){
        return object == null ? "" : object.toString();
    }


    public static String removeCurrency(String param){
        return param.replace("￥", "").replace("$", "");
    }

    public static String Clob2String(java.sql.Clob clob) {
        String clobStr = "";
        if(clob == null) {
        	return "";
        }
        try {
            Reader inStream = clob.getCharacterStream();
            char[] c = new char[(int) clob.length()];
            inStream.read(c);
            clobStr = new String(c);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clobStr;
    }
}
