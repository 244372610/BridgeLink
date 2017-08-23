package com.orient.util;

import com.orient.webService.YGXF_GETNowTime_WEBSERVICE;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Decoder;

import java.util.Iterator;


public class DataDealList {


	/**
	 * 取得共有多少条信访记录
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getXFCountByXML(String str) throws DocumentException {
		String result = "";
		Document document = DocumentHelper.parseText(str);
		Element root = document.getRootElement();
		Element body = root.element("Body");
		Element TS_Get_ListResponse = body.element("SearchInfoZSJBList1Response");
		Element allcount = TS_Get_ListResponse.element("allcount");
		result = allcount.getText();
		return result;
	}

	/**
	 * 取得共有多少条信访记录
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getXFInfotByXML(String str) throws DocumentException {
		Document document = DocumentHelper.parseText(str);
		Element root = document.getRootElement();
		Element body = root.element("Body");
		Element GetInfoResponse = body.element("GetInfoResponse");
		Element xfInfo = GetInfoResponse.element("GetInfoResult");
		//输出json内容
		return ParseXML.xmlString2Json(xfInfo.asXML()).getJSONObject("GetInfoResult");
	}




	/**
	 * 取XML最终子节点的值
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getResultByXML(String str) throws DocumentException {
		String result = "";
		Document document = DocumentHelper.parseText(str);
		Element root = document.getRootElement();
		root = getResultNode(root);
		result = root.getText();
		return result;
	}


	/**
	 * 找出XML中最后一个节点，即存放MD5密文的节点
	 *
	 * @param e
	 * @return Element节点
	 */
	private static Element getResultNode(Element e) {
		Element node = e;
		Iterator<Element> it = e.elementIterator();
		while (it.hasNext()) {
			node = it.next();
			return getResultNode(node);
		}
		return node;
	}

	/**
	 * MD5解密函数
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String doDecryption(String str) throws Exception {
		String result = "";
		Decode decodeTest = new Decode();
		decodeTest.doInit();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] encryptedData = decoder.decodeBuffer(str);
		byte[] x = decodeTest.decrypt(encryptedData);
		result = new String(x, "utf-8");
		return result;
	}


}
