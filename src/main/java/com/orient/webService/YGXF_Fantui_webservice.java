package com.orient.webService;

import com.orient.util.HttpUrlUtil;
import net.sf.json.JSONObject;

public class YGXF_Fantui_webservice extends YGXF_webservice{

	public static String submitCompelete(JSONObject jsonObject){
		String soapRequest = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
				"  <soap:Header>\n" +
				"    <SoapUsernameAndPassword xmlns=\"service\">\n" +
				"      <MyUsername>string</MyUsername>\n" +
				"      <MyPassword>string</MyPassword>\n" +
				"    </SoapUsernameAndPassword>\n" +
				"  </soap:Header>\n" +
				"  <soap:Body>\n" +
				"    <R_InsertZSJB_ZB xmlns=\"service\">\n" +
				"      <infoid>"+jsonObject.getString("infoId")+"</infoid>\n" +  //信访编号
				"      <rdanweiid>"+""+"</rdanweiid>\n" +
				"      <rdanweiname>"+""+"string</rdanweiname>\n" +
				"      <sdanweiid>"+""+"</sdanweiid>\n" +
				"      <sdanweiname>"+""+"</sdanweiname>\n" +
				"      <sdeptid>"+""+"</sdeptid>\n" +
				"      <sdeptname>"+""+"</sdeptname>\n" +
				"      <suserid>"+""+"</suserid>\n" +
				"      <susername>"+""+"</susername>\n" +
				"      <handlecontent>"+jsonObject.getString("content")+"</handlecontent>\n" +  //处置意见
				"      <processmode>"+"自办"+"</processmode>\n" +
				"      <zsjbstate>"+jsonObject.getString("sdeptname")+"</zsjbstate>\n" +
				"      <isfankui>"+"0"+"</isfankui>\n" +
				"      <xbdanweiid>"+""+"</xbdanweiid>\n" +   //协办单位ID
				"      <MD5Value>"+YGXF_GETNowTime_WEBSERVICE.getTimeMD5()+"</MD5Value>\n" +
				"      <addusername>"+""+"</addusername>\n" +
				"      <receiveuserid>"+""+"</receiveuserid>\n" +
				"      <receiveusername>"+""+"</receiveusername>\n" +
				"      <dotime>"+YGXF_GETNowTime_WEBSERVICE.getNowTime()+"</dotime>\n" +
				"      <RECEIVEOVERTIME>"+YGXF_GETNowTime_WEBSERVICE.getNowTime()+"</RECEIVEOVERTIME>\n" +
				"      <waycode>"+"050000"+"</waycode>\n" +
				"      <BLJGDM>"+""+"</BLJGDM>\n" +
				"      <_isend>"+jsonObject.getString("isSendMsg")+"</_isend>\n" +
				"      <_servicecontent>"+"办结"+"</_servicecontent>\n" +
				"      <hfgznr>"+""+"</hfgznr>\n" +
				"      <sjly>"+""+"</sjly>\n" +
				"      <bljglb>"+""+"</bljglb>\n" +
				"    </R_InsertZSJB_ZB>\n" +
				"  </soap:Body>\n" +
				"</soap:Envelope>";
		return HttpUrlUtil.callWs(URL, soapRequest);
	}

}

