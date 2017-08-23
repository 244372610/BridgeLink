package com.orient.service;

import com.orient.util.ConfigInfo;
import com.orient.util.LoginInfo;
import com.orient.webService.YGXF_Fantui_webservice;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 办结反推
 */
@Service("backStep")
public class BackStepImpl implements BackStep {

    @Override
    public String submit12345(JSONObject jsonObject) {
        // 新吴区城管局 009001新吴区城管局
        // 新吴区住建局 009002新吴区住建局
        // 新吴区江溪街道 009003新吴区江溪街道
        // 新吴区旺庄街道 009004新吴区旺庄街道
        // 新吴区梅村街道 009005新吴区梅村街道
        // 新吴区新安街道 009006新吴区新安街道
        // 新吴区鸿山街道 009007新吴区鸿山街道
        // 新吴区硕放街道 009008新吴区硕放街道
        // 新吴区组织部 009009新吴区组织部
        // 新吴区纪工委 009010新吴区纪工委
        // 新吴区宣传部 009011新吴区宣传部
        // 新吴区规划局 009013新吴区规划局
        // 新吴区地经局 009014新吴区地经局
        // 新吴区民政卫计局 009015新吴区民政卫计局
        // 新吴区安监环保局 009016新吴区安监环保局
        // 新吴区教育文体局 009018新吴区教育文体局
        // 新吴区吴博园 009019新吴区吴博园
        // 新吴区工博园 009020新吴区工博园
        // 新吴区人防办 009021新吴区人防办
        // 新吴区公安分局 009022新吴区公安分局
        // 新吴区国土分局 009023新吴区国土分局
        // 新吴区市场监管局 009024新吴区市场监管局
        // 新吴区交通运输分局 009025新吴区交通运输分局
        // 新吴区新发集团 009027新吴区新发集团
        // 新吴区软件园 009028新吴区软件园
        // 新吴区星洲工业园 009029新吴区星洲工业园
        // 新吴区科技职业学院 009030新吴区科技职业学院
        // 新吴区人社局 009031新吴区人社局
        // 新吴区房屋登记中心 009032新吴区房屋登记中心
        // 新吴区经发局 009034新吴区经发局
        // 新吴区国税局 009035新吴区国税局
        // 新吴区高新水务 009036新吴区高新水务
        // 新吴区招商服务局 009037新吴区招商服务局
        // 新吴区科信局 009038新吴区科信局
        // 新吴区财政局 009039新吴区财政局
        System.out.println(jsonObject);
        JSONObject para = new JSONObject();
        para.put("audingInfo",jsonObject.getString("content"));
        para.put("caseId",jsonObject.getString("infoId"));
        para.put("depOpen","depCodeAuding:\"009002\"");//参考上面的部门ID
        para.put("hasLast","0");
        para.put("isNetCase","1");//不知道指的是什么 ，我获取到的和
        para.put("isSendMsg",jsonObject.getString("isSendMsg"));//是否发送短信(1代表发送，0代表不发送)
        para.put("isclose","1"); //不公开
        para.put("isopen","0");//公开
        //如果公开depOpen为1,如果不公开depOpen为0，否则为2
        para.put("depOpen",jsonObject.getString("isopen")!=""?jsonObject.getString("isopen"):2);
        para.put("lastEndTime",jsonObject.getString("lastEndTime"));
        para.put("lastFlowState","20");//不知道指的是什么
        para.put("netType","0"); //不知道指的是什么
        para.put("sourceType","8");  //不确定指的是什么
        para.put("txtLaster",jsonObject.getString("lastHandleUser"));
        para.put("txtLasterPhone",jsonObject.getString("lastHandleUserPhone"));
        return doPost(ConfigInfo.DOMAIN+"Case/ShenQingBanJie",para, "UTF-8");
    }

    @Override
    public String submitYGXF(JSONObject jsonObject) {
        //Infoid
       // this.dbs.R_InsertZSJB_ZB(this.Infoid, this.User.Danwei.ID, this.User.Danwei.NAME, this.User.Danwei.ID, this.User.Danwei.NAME,
         //       this.User.Dept.ID, this.User.Dept.NAME, this.User.ID, this.User.REALNAME, this.tb_opinion_ziban.Text.Trim().Replace("'", "''").Replace("','", "'，'"),
           //     this.cb_processmode_ziban.Text, ZSJBState.程序性受理告知.GetHashCode().ToString(), "0", "",
             //   LocalMsg.MD5String(this.dbs.GetNowTime()), this.User.REALNAME, this.User.ID, this.User.REALNAME, this.dbs.GetNowTime().ToString(),
               // this.dbs.GetNowTime().ToString(), "040100", this.User.Danwei.EXT3, "0", "", this.tb_opinion_ziban.Text.Trim(), this.info.Sjly, this.User.Danwei.Ext4)).StartsWith("错误"))
        return YGXF_Fantui_webservice.submitCompelete(jsonObject);
    }

    public String doPost(String url, Map<String,String> map, String charset){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();//标准Cookie策略
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(LoginInfo.getFantuiInstance().getCookieStore()).build();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
