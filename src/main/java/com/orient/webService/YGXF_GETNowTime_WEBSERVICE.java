package com.orient.webService;

import com.orient.util.DataDealList;
import com.orient.util.HttpUrlUtil;
import com.orient.util.MD5;
import org.dom4j.DocumentException;

import java.time.LocalDateTime;

/**
 * Created by sunweipeng on 2017/8/21.
 */
public class YGXF_GETNowTime_WEBSERVICE extends YGXF_webservice {

    private static String getTime() throws DocumentException {
        String soapRequest = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Header>\n" +
                "    <SoapUsernameAndPassword xmlns=\"service\">\n" +
                "      <MyUsername>"+USERNAME+"</MyUsername>\n" +
                "      <MyPassword>"+PASSWORD+"</MyPassword>\n" +
                "    </SoapUsernameAndPassword>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n" +
                "    <GetNowTime xmlns=\"service\" />\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        return HttpUrlUtil.callWs(URL, soapRequest);
    }

    public static String getNowTime(){
        try {
            return DataDealList.getResultByXML(getTime());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return LocalDateTime.now().toString();
    }

    /**
     * 获取系统时间的MD5加密
     * @return
     * @throws Exception
     */
    public static String getTimeMD5(){
        String result = "";
        MD5 md = new MD5();
        String webStr = getNowTime();
        result = md.getMD5ofStr32(webStr);
        return result;
    }

    public static void main(String[] args) throws DocumentException {
        System.out.println(LocalDateTime.now().toString());
    }
}
