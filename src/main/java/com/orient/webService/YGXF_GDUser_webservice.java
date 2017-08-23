package com.orient.webService;

import com.orient.util.DataDealList;
import com.orient.util.HttpUrlUtil;
import com.orient.util.ParseXML;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YGXF_GDUser_webservice extends YGXF_webservice{

	public static String getGDInfo() throws Exception{

		String soapEntity = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
				"  <soap:Header>\n" +
				"    <SoapUsernameAndPassword xmlns=\"service\">\n" +
				"      <MyUsername>"+USERNAME+"</MyUsername>\n" +
				"      <MyPassword>"+PASSWORD+"</MyPassword>\n" +
				"    </SoapUsernameAndPassword>\n" +
				"  </soap:Header>\n" +
				"  <soap:Body>\n" +
				"    <TS_GetIDCARD_One xmlns=\"service\">\n" +
				"      <mid>320222195309070979</mid>\n" +
				"    </TS_GetIDCARD_One>\n" +
				"  </soap:Body>\n" +
				"</soap:Envelope>";
		return HttpUrlUtil.callWs(URL, soapEntity);
	}


	public static void main(String[] args) throws Exception {
		DataDealList ddl = new DataDealList();
		System.out.println(getGDInfo());
		System.out.println(ParseXML.xmlString2Json(getGDInfo()).getJSONObject("Envelope").getJSONArray("Body").getJSONObject(0).getJSONArray("TS_GetIDCARD_OneResponse").getJSONObject(0).getString("TS_GetIDCARD_OneResult"));
		String str = ParseXML.xmlString2Json(getGDInfo()).getJSONObject("Envelope").getJSONArray("Body").getJSONObject(0).getJSONArray("TS_GetIDCARD_OneResponse").getJSONObject(0).getString("TS_GetIDCARD_OneResult");

String json = ddl.doDecryption(str.substring(2,str.length()-2));
		System.out.println(json);
		//System.out.println(new DataDealList().getResultByXML(getGDInfo("111721680625")));
	}


}
