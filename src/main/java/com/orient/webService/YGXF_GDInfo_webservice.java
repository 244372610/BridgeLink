package com.orient.webService;

import com.orient.util.DataDealList;
import com.orient.util.HttpUrlUtil;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;

public class YGXF_GDInfo_webservice extends YGXF_webservice{

	private static String getXFXML(String gdId) throws DocumentException{
		String soapRequest = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
						"  <soap:Header>\n" +
						"    <SoapUsernameAndPassword xmlns=\"service\">\n" +
						"      <MyUsername>"+USERNAME+"</MyUsername>\n" +
						"      <MyPassword>"+PASSWORD+"</MyPassword>\n" +
						"    </SoapUsernameAndPassword>\n" +
						"  </soap:Header>\n" +
						"  <soap:Body>\n" +
						"    <GetInfo xmlns=\"service\">\n" +
						"      <infoid>"+gdId+"</infoid>\n" +
						"    </GetInfo>\n" +
						"  </soap:Body>\n" +
						"</soap:Envelope>";
		return HttpUrlUtil.callWs(URL, soapRequest);
	}

	public static JSONObject getXFInfo(String xfId) throws DocumentException {
		String xml = getXFXML(xfId);
		//System.out.println(xml);
		JSONObject xfJson = DataDealList.getXFInfotByXML(xml);
		return xfJson;
	}

	public static void main(String[] args) throws DocumentException {
		getXFInfo("111717828455");
	}
}

