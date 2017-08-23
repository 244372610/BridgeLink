package com.orient.task;

import com.orient.bean.Appeal;
import com.orient.bean.AppealBill;
import com.orient.business.BridgeBusiness;
import com.orient.webService.YGXF_GDInfo_webservice;
import com.orient.webService.YGXF_GDlist_webservice;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by sunweipeng on 2017/7/20.
 */
@Component("loadDataYGXF")
public class LoadDataYGXF {

    public static final AtomicInteger success = new AtomicInteger(0);
    private Logger log = Logger.getLogger(LoadDataYGXF.class);
    @Autowired
    private BridgeBusiness bridgeBusiness;

    //每天的凌晨0点开始
    //@Scheduled(cron = "0 55 9 * * ?")
    public void runTask() throws IOException, InterruptedException {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData() throws Exception {
        success.set(0);
        List<String> xfIds = YGXF_GDlist_webservice.getAllData();
        Iterator<String> iterator = xfIds.iterator();
        int dealNum = 0;
        CountDownLatch countDownLatch = new CountDownLatch(xfIds.size());
        while(iterator.hasNext()){
            dealNum++;
            AppealBill appealbill = new AppealBill();
            appealbill.setC_SQBH_62(iterator.next());
            ThreadPoolFactory.getInstance().getConsumeThreadPool().submit(new HandleYGXF(appealbill, bridgeBusiness,countDownLatch));
        }
        while(countDownLatch.getCount()!=0) {  //防止假中断
            try {
                countDownLatch.await();
            }catch(InterruptedException e) {
               // e.printStackTrace();
            }
        }
        log.info("本次成功录入了 "+success+" 条记录");
        int failNum = dealNum-success.get();
        log.info("本次共处理了 "+dealNum+" 条记录，失败了"+failNum+"条记录");
    }

    public static void main(String[] args) throws Exception {
        AppealBill appealBill = new AppealBill();
        appealBill.setC_SQBH_62("3202002017072100001");
        new HandleYGXF(appealBill,null,null).run();
    }
}
