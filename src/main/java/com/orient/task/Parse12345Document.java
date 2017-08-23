package com.orient.task;

import com.orient.bean.Appeal;
import com.orient.bean.AppealBill;
import com.orient.bean.AppealUser;
import com.orient.business.BridgeBusiness;
import com.orient.util.ConfigInfo;
import com.orient.util.LoginInfo;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析12345得到的html文件
 */
public class Parse12345Document implements Runnable {

    private Logger log = Logger.getLogger(Parse12345Document.class);

    private BridgeBusiness bridgeBusiness;

    private CountDownLatch countDownLatch;

    private Appeal appeal;

    public Parse12345Document(Appeal appeal, BridgeBusiness bridgeBusiness,CountDownLatch countDownLatch) {
        this.bridgeBusiness = bridgeBusiness;
        this.appeal = appeal;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        String URL = ConfigInfo.SERVER + "Case/CaseDetail?id=" + appeal.getCasecodeid();
        try {
            Document document = null;
            try {
                document = Jsoup.connect(URL).cookies(LoginInfo.getPollDataInstance().getCookies()).get();
            } catch (IOException e) {//如果失败就在尝试一次，如果还失败就判定读取失败
                int count = 10; //如果超时则执行10次，直到document不为null
                while(count-->0) {
                    document = Jsoup.connect(URL).cookies(LoginInfo.getPollDataInstance().getCookies()).get();
                    if(document!=null){
                        break;
                    }
                }
            }
            Elements elements = document.select("td.formTitle");
            Map<String, String> content = new HashMap<>();
            for (Element element : elements) {
                String value = element.nextElementSibling().text();
                content.put(element.text(), value);
                if ("附件".equals(element.text()) && !"".equals(value.trim())) {
                    String url = element.nextElementSibling().child(0).attr("onclick");
                    url = ConfigInfo.SERVER + url.substring(13, url.length() - 2);
                    FileOutputStream out = null;
                    try {
                        Connection.Response response = Jsoup.connect(url).cookies(LoginInfo.getPollDataInstance().getCookies()).ignoreContentType(true).execute();
                        out = new FileOutputStream(new java.io.File(ConfigInfo.FileDirctory+value));
                        out.write(response.bodyAsBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    content.put("附件地址", url);
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
                    userDocument = Jsoup.connect(ConfigInfo.SERVER + "Case/CustomerDetail?id=" + customerId).cookies(LoginInfo.getPollDataInstance().getCookies()).get();
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
                user.setID(bridgeBusiness.querySQYHSeqNexVal());
                user.setC_SQYHBH_65(customerId);
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            AppealBill appealBill = new AppealBill();
            appealBill.setC_SQBH_62(content.get("工单编号"));
            appealBill.setC_XDSJ_62(dateFormat.parse(content.get("下单时间")));
            appealBill.setC_GDLY_62(content.get("工单来源"));
            appealBill.setC_YWLX_62(content.get("诉求类别"));
            appealBill.setC_SQLX_62(content.get("工单类型"));
            appealBill.setC_YW_TYPE_62(content.get("业务类别"));
            appealBill.setC_SFBM_62("是".equals(content.get("是否保密"))?"1":"0");
            appealBill.setC_SFXFJ_62("是".equals(content.get("是否信访件"))?"1":"0");
            appealBill.setC_SQNR_62(content.get("诉求内容"));
            appealBill.setC_CLSX_62(dateFormat.parse(content.get("处理时限")));
            appealBill.setC_CLZT_62(appeal.getCasestate());
            appealBill.setC_LYID_62(appeal.getLyid());
            appealBill.setC_SSPT_62("12345");
            appealBill.setC_SQZT_62("未上报");
            appealBill.setT_SQYH_500_ID(user.getID());
            if(bridgeBusiness.getAppealCount(appealBill)>0) {
                bridgeBusiness.updateAppeal(appealBill);  //如果系统已经存在对应记录，则执行更新
            }else {
                bridgeBusiness.insertAppeal(appealBill);
                try {
                    bridgeBusiness.insertUser(user);//这里通过数据库层面的主键不能重复来保证不会插入相同的用户记录，所以不用先判断是否数据库中已经有该用户信息
                }catch(Exception el ) {}
            }
            LoadData12345.success.incrementAndGet();//成功
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error(appeal.getCasecodeid()+"数据处理失败，工单编号"+ appeal.getCasecodeid() +"  url"+URL);
        }finally {
            countDownLatch.countDown();
        }
    }

}