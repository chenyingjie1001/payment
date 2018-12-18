package com.firesoon.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description: 配置加载类
 * @author:
 * @date: 2018/9/18 19:33
 */
@Slf4j
public class PropertiesUtil {

    public static Properties properties;

    static {
        properties = new Properties();
        InputStream in = null;
        InputStream input = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
            input = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf.properties");
            properties.load(in);
            properties.load(input);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (input != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
