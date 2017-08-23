package com.orient.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by sunweipeng on 2017/7/20.
 */
public class ConfigInfo {

    private static Properties properties = null;


    static {
        properties = new Properties();
        try {
            InputStream inputStream = ConfigInfo.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }


    public static final int PAGESIZE = Integer.valueOf(properties.getProperty("PAGESIZE","50"));
    public static final String LoadDataURL= properties.getProperty("LISTURL");
    public static final String USERNAME = properties.getProperty("USERNAME");
    public static final String PASSWORD = properties.getProperty("PASSWORD");
    public static final String FANTUIUSERNAME = properties.getProperty("FANTUIUSERNAME");
    public static final String FANTUIPASSWORD = properties.getProperty("FANTUIPASSWORD");
    public static final String CODEURL = properties.getProperty("CODEURL");
    public static final String LOGINURL = properties.getProperty("LOGINURL");
    public static final String SERVER = properties.getProperty("SERVER");
    public static final String DOMAIN = properties.getProperty("DOMAIN");//"58.215.18.122";
    public static int LoginFailThreshold =Integer.valueOf(properties.getProperty("LoginFailThreshold","100"));
    public static final String FileDirctory = properties.getProperty("FileDirctory");
    public static final String MAIL_RECEIVER = properties.getProperty("MAIL_RECEIVER");
    public static final String TESSERACTPATH = properties.getProperty("tesseractPath");

}
