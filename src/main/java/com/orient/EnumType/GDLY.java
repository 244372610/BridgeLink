package com.orient.EnumType;

import java.util.HashMap;
import java.util.Map;

public class GDLY {
    //工单来源
    public static Map<String, String> gdlymap = new HashMap<>();
    //处理状态
    public static Map<String, String> clztmap = new HashMap<>();
    //网单类型
    public static Map<String, String> wdlxmap = new HashMap<>();
    //业务类型
    public static Map<String, String> ywlxmap = new HashMap<>();
    //工单类型
    public static Map<String, String> gdlxmap = new HashMap<>();

    static {
        gdlymap.put("0","不限");
        gdlymap.put("3", "传真");
        gdlymap.put("13","大走访工单");
        gdlymap.put("5","东林书院");
        gdlymap.put("4","短信");
        gdlymap.put("6","二泉论坛");
        gdlymap.put("1","来电");
        gdlymap.put("9","来信");
        gdlymap.put("12","省12345");
        gdlymap.put("14","省12345（大走访）");
        gdlymap.put("8","网上");
        gdlymap.put("10","微信平台");
        gdlymap.put("2","邮件");
        gdlymap.put("11","政务办");
        gdlymap.put("7","智慧江苏");
    }

    static {
        clztmap.put("0","不限");
        clztmap.put("1","草稿");
        clztmap.put("2","处理中");
        clztmap.put("3","后台组处理");
        clztmap.put("4","话务员回访");
        clztmap.put("5","退回话务员");
        clztmap.put("6","无效工单");
        clztmap.put("7","已办结");
        clztmap.put("8","已冻结");
        clztmap.put("9","预审岗处理");
        clztmap.put("10","申请办结");
        clztmap.put("11","已删除");
    }

    static {
        wdlxmap.put("0","不限");
        wdlxmap.put("12345","12345工单");
        wdlxmap.put("1","市长信箱");
        wdlxmap.put("2","咨询投诉");
        wdlxmap.put("3","部门信箱");
    }

    static {
        ywlxmap.put("0","不限");
        ywlxmap.put("1","咨询类");
        ywlxmap.put("2","求助类");
        ywlxmap.put("3","建议类");
        ywlxmap.put("4","投诉类");
        ywlxmap.put("5","举报类");
        ywlxmap.put("6","表扬类");
        ywlxmap.put("7","其他类");
    }

    static {
        gdlxmap.put("0","不限");
        gdlxmap.put("1","新问题");
        gdlxmap.put("2","同工单");
        gdlxmap.put("3","补充工单");
        gdlxmap.put("4","咨询工单");
        gdlxmap.put("5","接管工单");
    }
}