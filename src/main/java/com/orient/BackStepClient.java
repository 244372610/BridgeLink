package com.orient;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sunweipeng on 2017/8/21.
 * 反推客户端程序
 */
@Component
public class BackStepClient {

    
    public void submit() {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sun","weipeng");
        restTemplate.postForEntity("http://localhost:8080/OrientLink/backStep12345Service",jsonObject,JSONObject.class);

    }
}