package com.orient.webService;


import com.orient.util.DataDealList;
import com.orient.util.HttpUrlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YGXF_GDlist_webservice extends YGXF_webservice{

	public static String list(int page,int pageSize) {
		String soapRequest = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
				"  <soap:Header>\n" +
				"    <SoapUsernameAndPassword xmlns=\"service\">\n" +
				"      <MyUsername>"+USERNAME+"</MyUsername>\n" +
				"      <MyPassword>"+PASSWORD+"</MyPassword>\n" +
				"    </SoapUsernameAndPassword>\n" +
				"  </soap:Header>\n" +
				"  <soap:Body>\n" +
				"    <SearchInfoZSJBList1 xmlns=\"service\">\n" +
				"      <where>GqXqg0AT050=</where>\n" +
				"      <fieldname>Uns41V16jRWhfMO8cVp8A+ww+qDVpeR+3UAowM6pRKT/NEttZliM2g4WdU3vgChWeGxlRQfMtMpJrpcNaihZ8O2e8lK37pT1ze4SJygH/TQ+mo01c5H1iPy5ZsJQcglQsrNlnBaq7NhxoLgm0NFr9wXj8YOMvokA1MkHfmMvPOVor7aLk1YA8w==</fieldname>\n" +
				"      <orderby>i.visittime desc</orderby>\n" +
				"      <access>158</access>\n" +
				"      <pagesize>"+pageSize+"</pagesize>\n" +
				"      <page>"+page+"</page>\n" +
				"      <allcount>0</allcount>\n" +
				"    </SearchInfoZSJBList1>\n" +
				"  </soap:Body>\n" +
				"</soap:Envelope>\n";
		return HttpUrlUtil.callWs(URL, soapRequest);
	}
	
	public static String getTotalNum() throws DocumentException {
		return DataDealList.getXFCountByXML(list(PAGESIZE,1));
	}

	public static List<String> getAllData() throws DocumentException {
		String rowNum = getTotalNum();
		int pageSize = 1000;
		int page = Integer.valueOf(rowNum)%pageSize==0?Integer.valueOf(rowNum)/pageSize:Integer.valueOf(rowNum)/pageSize+1;
		List<String> xfIds = new ArrayList<>(Integer.valueOf(rowNum));
		for(int i=1;i<=page;i++) {
			JSONArray array = JSONObject.fromObject(DataDealList.getResultByXML(list(i,pageSize))).getJSONArray("Rows");
			Iterator<JSONArray> iterator = array.iterator();
			while(iterator.hasNext()) {
				xfIds.add(iterator.next().getString(0));
			}
		}
		return xfIds;
	}
	
}
