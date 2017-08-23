package com.orient.util;

import net.sf.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunweipeng on 2017/8/14.
 */
public class ParseXML {
    /**
     * xml string字符串转换成JSON
     * @param xmlStr
     * @return
     */
    public static JSONObject xmlString2Json(String xmlStr){
        JSONObject json = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            json.put(root.getName(), iterateElement(root));
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 迭代
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map<String,Object> iterateElement(Element element) {
        List<Element> jiedian = element.getChildren();
        Element et = null;
        Map<String,Object> obj = new HashMap<String,Object>();
        List<Object> list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList<Object>();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0) {
                    obj.put(et.getName(), "");
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    list = (List<Object>) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List<Object>) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                if(list.size()==1){
                    obj.put(et.getName(),list.get(0));
                }else {
                    obj.put(et.getName(), list);
                }
            }
        }
        return obj;
    }
}
