package com.orient.task;

import com.orient.bean.AppealBill;
import com.orient.bean.AppealUser;
import com.orient.business.BridgeBusiness;
import com.orient.webService.YGXF_GDInfo_webservice;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

/**
 * 解析阳关信访
 */
public class HandleYGXF implements Runnable {

    private Logger log = Logger.getLogger(HandleYGXF.class);

    private BridgeBusiness bridgeBusiness;

    private CountDownLatch countDownLatch;

    private AppealBill appealBill;

    public HandleYGXF(AppealBill appealBill, BridgeBusiness bridgeBusiness, CountDownLatch countDownLatch) {
        this.bridgeBusiness = bridgeBusiness;
        this.appealBill = appealBill;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        JSONObject xfInfo = null;
        try {
            xfInfo = YGXF_GDInfo_webservice.getXFInfo(appealBill.getC_SQBH_62());
            //log.info(xfInfo);
            //装配用户信息开始
            AppealUser user = new AppealUser();
            user.setC_XM_65(xfInfo.getString("visitorname"));//姓名/昵称
            user.setC_LXFS_65(xfInfo.getString("mobilephone"));//联系电话
            user.setC_SFZ_65(xfInfo.getString("idcard"));//证件号码
            user.setC_ZZ_65(xfInfo.getString("telphone"));//住宅电话
            user.setC_XXDZ_65(xfInfo.getString("questionarea")+xfInfo.getString("address"));//住址
            String seq = bridgeBusiness.querySQYHSeqNexVal();
            user.setC_SQYHBH_65("YGXF-"+seq);  //生成用户编号
            user.setID(seq);
            //装配用户信息结束

            //装配信访信息
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            appealBill.setC_XDSJ_62(df.parse(xfInfo.getString("visittime")));  //上访时间
            appealBill.setC_GDLY_62(xfInfo.getString("way"));//诉求方式
            appealBill.setC_YWLX_62(xfInfo.getString("questiontype"));
            appealBill.setC_SQLX_62(xfInfo.getString("source"));//工单类型
            appealBill.setC_YW_TYPE_62(xfInfo.getString("aim"));//业务类型
            appealBill.setC_SFBM_62(xfInfo.getString("isopen"));
            appealBill.setC_SFXFJ_62("1");
            appealBill.setC_SQNR_62(xfInfo.getString("content"));//诉求内容
            appealBill.setC_CLSX_62(df.parse(xfInfo.getString("limittime"))); //处理时限
            appealBill.setC_CLZT_62("0".equals(xfInfo.getString("isend"))?"处理中":"已办结");
            appealBill.setC_LYID_62("");//阳关信访不存在来源ID
            appealBill.setC_SQZT_62("未上报");
            appealBill.setC_SQBT_62(xfInfo.getString("title"));
            appealBill.setC_SSPT_62("阳光信访");
            if(bridgeBusiness.getAppealCount(appealBill)>0) {
                bridgeBusiness.updateAppeal(appealBill);  //如果系统已经存在对应记录，则执行更新
            }else {
                //阳光信访和用户存放在一张表中，当更新信访信息的时候不需要变动我们的诉求用户表，只有在第一次插入的时候需要同时插入用户信息
                appealBill.setT_SQYH_500_ID(user.getID());
                bridgeBusiness.insertAppeal(appealBill);
                bridgeBusiness.insertUser(user);
            }
            LoadDataYGXF.success.incrementAndGet();//成功
        } catch (DocumentException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("记录读取失败"+xfInfo);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("解析日期格式失败"+xfInfo);
        } finally {
            countDownLatch.countDown();
        }
    }

}
