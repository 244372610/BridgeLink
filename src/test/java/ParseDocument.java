
import com.orient.bean.AppealBill;
import com.orient.bean.AppealUser;
import com.orient.util.LoginInfo;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseDocument {

    private Logger log = Logger.getLogger(ParseDocument.class);


    public static void main(String[] args) {
            Document document = null;
            try {
                document = Jsoup.connect("http://58.215.18.122:8090/for12345/Case/CaseDetail?id=WX201708020053" ).cookies(LoginInfo.getPollDataInstance().getCookies()).get();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            Elements elements = document.select("td.formTitle");
            Map<String, String> content = new HashMap<>();
            for (Element element : elements) {
                String value = element.nextElementSibling().text();
                content.put(element.text(), value);
                if("附件".equals(element.text())&&!"".equals(value.trim())){
                    String url = element.nextElementSibling().child(0).attr("onclick");
                    url = "http://58.215.18.122:8090/for12345/"+url.substring(13,url.length()-2);
                    FileOutputStream out = null;
                    try {
                        Connection.Response response = Jsoup.connect(url).cookies(LoginInfo.getPollDataInstance().getCookies()).ignoreContentType(true).execute();
                        out = new FileOutputStream(new java.io.File(value));
                        out.write(response.bodyAsBytes());
                    }catch(IOException e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            if(out!=null)
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    content.put("附件地址",url);
                }
            }
            Pattern pattern = Pattern.compile("showCustomerDetail\\(([0-9]+)\\)");
            Matcher matcher = pattern.matcher(document.body().toString());
            String customerId = "0";
            AppealUser user = new AppealUser();
            if (matcher.find()) {
                customerId = matcher.group(1);
                Document userDocument = null;
                try {
                    userDocument = Jsoup.connect("http://58.215.18.122:8090/for12345/Case/CustomerDetail?id=" + customerId).cookies(LoginInfo.getPollDataInstance().getCookies()).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements userelements = userDocument.select("td.formTitle");
                Map<String, String> userInfo = new HashMap<>();
                for (Element element : userelements) {
                    String value = element.nextElementSibling().text();
                    userInfo.put(element.text(), value);
                }
                user.setC_XM_65(userInfo.get("姓名/昵称"));
                user.setC_LXFS_65(userInfo.get("联系电话"));
                user.setC_SFZ_65(userInfo.get("证件号码"));
                user.setC_XXDZ_65(userInfo.get("住址"));
            }
            AppealBill appealBill = new AppealBill();
            appealBill.setC_SQBH_62(content.get("工单编号"));
            //appealBill.setC_XDSJ_62(content.get("下单时间"));
            appealBill.setC_GDLY_62(content.get("工单来源"));
            appealBill.setC_YWLX_62(content.get("诉求类别"));
            appealBill.setC_SQLX_62(content.get("工单类型"));
            appealBill.setC_YW_TYPE_62(content.get("业务类别"));
            appealBill.setC_SFBM_62(content.get("是否保密"));
            appealBill.setC_SFXFJ_62(content.get("是否信访件"));
            appealBill.setC_SQNR_62(content.get("诉求内容"));
            //appealBill.setC_CLSX_62(content.get("处理时限"));
        }
}