package com.orient.util;

import org.apache.http.impl.client.BasicCookieStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录信息
 */
public class LoginInfo {

    public static class PollDataLoginInfoHolder{
        private static Map<String,String> cookies;

        private static BasicCookieStore cookieStore;
        static {
            int count = 0;
            while (count < ConfigInfo.LoginFailThreshold) {
                count++;
                String ret = LoginUtil.login(ConfigInfo.USERNAME, ConfigInfo.PASSWORD);
                if ("0".equals(ret)) { //如果登录成功则跳出登录循环
                    break;
                }
            }
            cookieStore = LoginUtil.cookieStore;
            cookies = new HashMap<>();
            cookieStore.getCookies().forEach(cookie -> cookies.put(cookie.getName(), cookie.getValue()));
        }

        public Map<String,String> getCookies() {
            return cookies;
        }

        public BasicCookieStore getCookieStore() {
            return cookieStore;
        }

        private PollDataLoginInfoHolder(){}
        private static final PollDataLoginInfoHolder instance = new PollDataLoginInfoHolder();
        public PollDataLoginInfoHolder getInstance() {
            return instance;
        }
    }

    public static class FantuiLoginInfoHolder{
        private static Map<String,String> cookies;

        private static BasicCookieStore cookieStore;
        static {
            int count = 0;
            while (count < 0) {
                count++;
                String ret = LoginUtil.login(ConfigInfo.FANTUIUSERNAME, ConfigInfo.FANTUIPASSWORD);
                if ("0".equals(ret)) { //如果登录成功则跳出登录循环
                    break;
                }
            }
            cookieStore = LoginUtil.cookieStore;
            cookies = new HashMap<>();
            cookieStore.getCookies().forEach(cookie -> cookies.put(cookie.getName(), cookie.getValue()));
        }

        public Map<String,String> getCookies() {
            return cookies;
        }

        public BasicCookieStore getCookieStore() {
            return cookieStore;
        }

        private FantuiLoginInfoHolder(){}
        private static final FantuiLoginInfoHolder instance = new FantuiLoginInfoHolder();
        public FantuiLoginInfoHolder getInstance() {
            return instance;
        }
    }

    private LoginInfo() {}

    public static PollDataLoginInfoHolder getPollDataInstance(){
        return PollDataLoginInfoHolder.instance;
    }

    public static FantuiLoginInfoHolder getFantuiInstance() {
        return FantuiLoginInfoHolder.instance;
    }
}
