package com.orient.controller;

import com.orient.service.BackStep;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sunweipeng on 2017/7/25.
 */
@RestController
public class BackStepController {


    @Autowired
    private BackStep backStep;

    //这里一定要加上RequestBody或者Valid注解，不然会报参数错误
    @RequestMapping("/backStep12345Service")
    @ResponseStatus(HttpStatus.CREATED)
    public String backStep12345Service(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        return backStep.submit12345(jsonObject);
    }

    @RequestMapping("/backStepYGXFService")
    @ResponseStatus(HttpStatus.CREATED)
    public String backStepYGXFService(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        return backStep.submitYGXF(jsonObject);
    }


    //从系统传过来的json串
    //{'fxt':'12345/阳光信访','infoId':'工单编号/信访单号','content':'处理意见','masterDeptId':'主办单位ID','masterDeptName':'主办单位名称','lastHandleUser':'最终经办人姓名','lastHandleUserPhone':'经办人电话号码','slaveDeptId':'协办单位ID','masterDeptName':'协办单位名称','isSendMsg':'1/0','isopen':'1/0','lastEndTime:'处理时限'}
    @RequestMapping("/backStep")
    @ResponseStatus(HttpStatus.CREATED)
    public String backStep(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        if("12345".equals(jsonObject.getString("fxt"))) {
            return backStep.submit12345(jsonObject);
        }else if("阳光信访".equals(jsonObject.getString("fxt"))){
            return backStep.submitYGXF(jsonObject);
        }
        return "系统参数错误";
    }
}
