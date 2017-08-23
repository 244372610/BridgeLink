package com.orient.util;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by changxk on 2017/7/10.
 */
public class LoginUtil {

    private static Logger log = Logger.getLogger(LoginUtil.class);
    public static BasicCookieStore cookieStore = null;

    public static String login(String username,String password){
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();//标准Cookie策略
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        String code = getCode(httpClient);
        String retCode = doLogin(username, password, code, httpClient);
        return retCode;
    }

    /**
     * 获取验证码
     * @param httpClient
     * @return
     */
    private static String getCode(HttpClient httpClient){
        double r = Math.random();
        String codeUrl = ConfigInfo.CODEURL + r;
        String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        File tempFile = null, cleanFile = null;
        try {
            tempFile = File.createTempFile (timestamp, "gif");
            HttpGet get = new HttpGet(codeUrl);
            //get.addHeader("Accept","image/webp,image/*,*/*;q=0.8");
            //get.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            FileOutputStream fos = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = content.read(buffer)) != -1){
                fos.write(buffer,0, len);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String code = "";
        OCRHelper ocr = new OCRHelper();
        try {
            String cleanFilePath = tempFile.getParent().concat("/clean");
            ocr.cleanImage(tempFile, cleanFilePath);
            cleanFile = new File(cleanFilePath.concat("/" + tempFile.getName()));
            code = ocr.recognizeText(cleanFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(tempFile != null)
                tempFile.delete();
            if(cleanFile != null)
                cleanFile.delete();
        }
        return code;
    }

    /**
     * 登录，返回0表示登录成功
     * @param userName
     * @param password
     * @param vercode
     * @param client
     * @return
     */
    private static String doLogin(String userName, String password, String vercode, HttpClient client){
        List<NameValuePair> postData = new ArrayList<>();
        NameValuePair account = new BasicNameValuePair("account", userName);
        NameValuePair code = new BasicNameValuePair("code", vercode);
        NameValuePair pwd = new BasicNameValuePair("pwd", password);
        NameValuePair roleId = new BasicNameValuePair("roleId", "11");
        NameValuePair roleName = new BasicNameValuePair("roleName", "二级单位");
        postData.add(account);
        postData.add(code);
        postData.add(pwd);
        postData.add(roleId);
        postData.add(roleName);
        String ret = "-1";
        try {
            HttpPost post = new HttpPost(ConfigInfo.LOGINURL);
            //post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
            //post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(postData, "UTF-8");
            post.setEntity(postEntity);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity,"GBK");
            if("0".equals(ret)){
                setCookieStore(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 登录成功后存储当前cookies
     * @param httpResponse
     */
    private static void setCookieStore(HttpResponse httpResponse) {
        cookieStore = new BasicCookieStore();
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        for(Header header : headers){
            String headValue = header.getValue();
            if(headValue.indexOf("user") >= 0){
                String userCookie = headValue.substring(("user=").length(),
                        headValue.indexOf(";"));
                BasicClientCookie cookie = new BasicClientCookie("user",
                        userCookie);
                cookie.setVersion(0);
                cookie.setDomain(ConfigInfo.DOMAIN);
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
                log.info("userCookie:" + userCookie);
            }
            if(headValue.indexOf("ASP.NET_SessionId") >= 0){
                String sessionCookie = headValue.substring(("ASP.NET_SessionId=").length(),
                        headValue.indexOf(";"));
                BasicClientCookie cookie = new BasicClientCookie("ASP.NET_SessionId",
                        sessionCookie);
                cookie.setVersion(0);
                cookie.setDomain(ConfigInfo.DOMAIN);
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
                System.out.println("ASP.NET_SessionId:" + sessionCookie);
            }
        }
    }
}
