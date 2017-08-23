package com.orient.bean;

import java.util.List;

/**
 * Created by sunweipeng on 2017/7/20.
 * 从12345取出的工单信息
 */
public class Appeal {
    //在获取得到的json串中，这几个字段的信息是什么不确定
    //callerid":"A00987BE-FC47-4C53-B2DC-487087CD6B1F","LockerName":null,"source1":"1","CaseType1":"1","casestate1":"2","appeal1":"24","FlowState":"10",
    //一体化工单中现在不确定的字段有：业务类型，是否保密，是否信访件，附件，用户信息
    //第多少条记录
    private String rownum;
    //诉求编号
    private String casecodeid;
    //下单时间
    private String createtime;
    //工单来源
    private String source;
    //业务类型
    private String caseType1;
    //诉求类别
    private String appeal;
    //工单类型
    private String casetype;
    //处理状态
    private String casestate;

    //来源ID
    private String lyid;

    private String callerid;

    private String lockername;

    private String source1;



    private String casestate1;

    private String appeal1;


    private String flowState;
    //诉求内容
    private String casecontent;
    //主办单位
    private String zhubanname;
    //话务员
    private String huawuyuanname;
    //处理时限
    private String outtime;
    //来电号码
    private String callerphone;

    private String yujing;

    private String position;


    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getCasestate() {
        return casestate;
    }

    public void setCasestate(String casestate) {
        this.casestate = casestate;
    }

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid;
    }

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    public String getLockername() {
        return lockername;
    }

    public void setLockername(String lockername) {
        this.lockername = lockername;
    }

    public String getSource1() {
        return source1;
    }

    public void setSource1(String source1) {
        this.source1 = source1;
    }

    public String getCaseType1() {
        return caseType1;
    }

    public void setCaseType1(String caseType1) {
        this.caseType1 = caseType1;
    }

    public String getCasestate1() {
        return casestate1;
    }

    public void setCasestate1(String casestate1) {
        this.casestate1 = casestate1;
    }

    public String getAppeal1() {
        return appeal1;
    }

    public void setAppeal1(String appeal1) {
        this.appeal1 = appeal1;
    }

    public String getCasecodeid() {
        return casecodeid;
    }

    public void setCasecodeid(String casecodeid) {
        this.casecodeid = casecodeid;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getCasecontent() {
        return casecontent;
    }

    public void setCasecontent(String casecontent) {
        this.casecontent = casecontent;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getZhubanname() {
        return zhubanname;
    }

    public void setZhubanname(String zhubanname) {
        this.zhubanname = zhubanname;
    }

    public String getHuawuyuanname() {
        return huawuyuanname;
    }

    public void setHuawuyuanname(String huawuyuanname) {
        this.huawuyuanname = huawuyuanname;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getCallerphone() {
        return callerphone;
    }

    public void setCallerphone(String callerphone) {
        this.callerphone = callerphone;
    }

    public String getYujing() {
        return yujing;
    }

    public void setYujing(String yujing) {
        this.yujing = yujing;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
