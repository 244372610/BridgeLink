package com.orient.task;

import com.orient.bean.Appeal;
import com.orient.business.BridgeBusiness;
import com.orient.util.ConfigInfo;
import com.orient.util.LoginInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by sunweipeng on 2017/7/20.
 */
@Component("loadData12345")
public class LoadData12345 {

    public static final AtomicInteger success = new AtomicInteger(0);
    private Logger log = Logger.getLogger(LoadData12345.class);
    @Autowired
    private BridgeBusiness bridgeBusiness;

    //每天的凌晨1点开始
    //@Scheduled(cron = "0 50 9 * * ?")
    public void runTask() {
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws IOException {
        success.set(0);
        int totalCount = getTotalNum(ConfigInfo.LoadDataURL);
        CountDownLatch countDownLatch = new CountDownLatch(totalCount);
        log.info("一共需要读取 "+totalCount+" 条记录");
        int dealNum = 0;//这次已经处理了多少条记录，包括失败的记录
        //一共有多少页
        int endPageIndex = totalCount/ConfigInfo.PAGESIZE+(totalCount%ConfigInfo.PAGESIZE==0?0:1);
        //这次从多少页开始读取
        Map<String, String> params = new HashMap<>();
        for (int i = 1; i <= endPageIndex; i++) {
            params.put("pageIndex", String.valueOf(i));
            Iterator<Appeal> appeals = getData(ConfigInfo.LoadDataURL, params).iterator();
            while(appeals.hasNext()){
                dealNum++;
                ThreadPoolFactory.getInstance().getConsumeThreadPool().submit(new Parse12345Document(appeals.next(), bridgeBusiness,countDownLatch));
            }
        }
        while(countDownLatch.getCount()!=0) {  //防止假中断
            try {
                countDownLatch.await();
            }catch(InterruptedException e) {
                // e.printStackTrace();
            }
        }
        log.info("本次成功录入了 "+success.get()+" 条记录");
        int failNum = dealNum-success.get();
        log.info("本次共处理了 "+dealNum+" 条记录，失败了"+failNum+"条记录");
    }

    /**
     * 得到12345系统中一共有多少条记录
     * @return
     * @throws IOException
     */
    private int getTotalNum(String url) throws IOException {
        HttpResponse response = getResponse(url);
        if(200!=response.getStatusLine().getStatusCode()){ //如果返回302说明发生了重定向，表明登录信息失效需要重新进行登录
            log.error("在获取数据列表的过程中用户信息丢失");
        }
        HttpEntity entity = response.getEntity();
        String ret = EntityUtils.toString(entity,"GBK");
        JSONObject jsonObject = JSONObject.fromObject(ret);
        String records = jsonObject.getString("records");
        return Integer.valueOf(records);
    }


    public List<Appeal> getData(String baseUrl, Map params) throws IOException {
        HttpResponse response = getResponse(baseUrl,params);
        if(200!=response.getStatusLine().getStatusCode()){ //如果返回302说明发生了重定向，表明登录信息失效需要重新进行登录
            log.error("在获取数据列表的过程中用户信息丢失");
        }
        HttpEntity entity = response.getEntity();
        String ret = EntityUtils.toString(entity,"UTF-8");
        ret = new String(ret.getBytes("UTF-8"),"UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(ret);
        JSONArray jsonArray = (JSONArray) jsonObject.get("rows");
        List<Appeal> appeals = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonTemp = jsonArray.getJSONObject(i);
            JSONObject json = new JSONObject();
            Set<Map.Entry<String,String>> entrySet = jsonTemp.entrySet();
            for(Map.Entry<String,String> entry: entrySet) {
                json.put(entry.getKey().toLowerCase(),entry.getValue());
            }

            Appeal appeal = new Appeal();
            appeal.setSource(json.getString("source"));
            appeal.setCasetype(json.getString("casetype"));
            appeal.setCasestate(json.getString("casestate"));
            appeal.setAppeal(json.getString("appeal"));
            appeal.setRownum(json.getString("rownum"));
            appeal.setCallerid(json.getString("callerid"));
            appeal.setLockername(json.getString("lockername"));
            appeal.setLyid(json.getString("id"));
            appeal.setSource1(json.getString("source1"));
            appeal.setCaseType1(json.getString("casetype1"));
            appeal.setCasestate1(json.getString("casestate1"));
            appeal.setAppeal1(json.getString("appeal1"));
            appeal.setCasecodeid(json.getString("casecodeid"));
            appeal.setFlowState(json.getString("flowstate"));
            appeal.setCasecontent(json.getString("casecontent"));
            appeal.setCreatetime(json.getString("createtime"));
            appeal.setZhubanname(json.getString("zhubanname"));
            appeal.setHuawuyuanname(json.getString("huawuyuanname"));
            appeal.setOuttime(json.getString("outtime"));
            appeal.setCallerphone(json.getString("callerphone"));
            appeal.setYujing(json.getString("yujing"));
            appeal.setPosition(json.getString("position"));
            appeals.add(appeal);
        }
        return appeals;
    }

    public HttpResponse getResponse(String url) throws IOException {
       return getResponse(url,new HashMap<String,String>());
    }

    public HttpResponse getResponse(String baseUrl,Map<String,String> params) throws IOException {
        String url = baseUrl;
        StringBuilder sb = new StringBuilder(baseUrl);
        if(params.size()>0) {
            if(sb.indexOf("?")==-1) {
                sb.append("?");
                for(String key:params.keySet()) {
                    sb.append(key).append("=").append(params.get(key)).append("&");
                }
                url = sb.substring(0, sb.lastIndexOf("&"));
            }else {
                for(String key:params.keySet()) {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
                url = sb.toString();
            }

        }
        log.info("请求URL为："+ url);
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();//标准Cookie策略
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(LoginInfo.getPollDataInstance().getCookieStore()).build();
        HttpResponse response = client.execute(post);
        return response;
    }
}
