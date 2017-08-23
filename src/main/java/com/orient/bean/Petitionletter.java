package com.orient.bean;

/**
 * Created by sunweipeng on 2017/8/14.
 * 阳关信访的信访件
 */
//<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body>
//<GetInfoResponse xmlns="service"><GetInfoResult>
//<Cfxba>0</Cfxba><Xfrgj>中国大陆</Xfrgj><Xfrgjhgatjmsf>GJ0237</Xfrgjhgatjmsf><Sqbz>0</Sqbz><Swbz>0</Swbz><Sabz>0</Sabz><Stbz>0</Stbz>
//<Sgbz>0</Sgbz><Sfssbz>0</Sfssbz><Sjly>320000</Sjly><fsdw /><Bfyraddress /><Bfyrhdw /><Bfyrzzmc /><Bfyrzzdm /><Bfyrzw /><Bfyrjb />
//<Bllx /><Yjsnr /><SDFS /><SDR /><SDSJ /><QSBZ /><Xfryj_Ext>0</Xfryj_Ext><Cxmzt /><CXM /><Nrmydpj>1</Nrmydpj><Important /><Ldps />
//<Xfxsid>200</Xfxsid><NRFLID>030201</NRFLID><SSXT>建设</SSXT><SSXTID>21</SSXTID><RDWTID /><SKSFLBZ>0</SKSFLBZ><JGCCBZ>1</JGCCBZ>
//<QGCCBZ>1</QGCCBZ><Xfcy>信访人因素</Xfcy><XfcyID>5</XfcyID><Visitorip /><Sjrs>1</Sjrs><Zjlx>居民身份证</Zjlx><Zjlxcode>1</Zjlxcode>
//<Formeraddresss>江苏省无锡市无锡新区旺庄街道办事处</Formeraddresss><Txdzdm>320200</Txdzdm><Xfmdcode>03</Xfmdcode><isskip>1</isskip><pcrate />
//<result /><registerplace /><tolevel>去县</tolevel><way>来访</way><iswho>0</iswho><pcid /><keyword /><limittime>2017-11-02T16:48:24</limittime>
//<islimit>0</islimit><istermination>0</istermination><islongcase>0</islongcase><source>县系统录入</source><type>正常信访</type>
//<endtime>0001-01-01T00:00:00</endtime><adddept>新吴区旺庄街道办事处</adddept><adddeptid>586</adddeptid>
//<servicecontent>过仁兴同志，您好！您2017年8月4日,通过来访反映的问题，信访编号111721680625,我单位已经受理，并正在办理之中，我们将尽快处理好您的信访事项。如您有新的情况或需要了解办理进度，可以拨打我单位电话进行咨询，咨询电话：。感谢您对我们工作的信任、理解和支持。谢谢使用。</servicecontent>
//<othername /><postcode /><infoid>111721680625</infoid><email /><visitorname>过仁兴</visitorname><idcard>320222195309070979</idcard><address>长欣公寓140号201室</address><adduser>侯轶玲</adduser><addorg>新吴区旺庄街道办事处</addorg><visittime>2017-08-04T16:42:50</visittime><visitornum>1</visitornum><telphone /><addtime>2017-08-04T16:48:24</addtime><mobilephone>15061801896</mobilephone><questionarea>江苏省无锡市无锡新区旺庄街道办事处</questionarea><isend>0</isend><isrepeat>0</isrepeat><questiontype>城乡建设_集体土地上房屋拆迁与补偿_安置补偿</questiontype><isxf>11</isxf><ispub>0</ispub><leaders /><title /><content>来访人反映目前自己符合政策性住房条件，但由于家庭条件困难要求给予照顾处理。其主要反映的诉求：1、解决政策性住房问题；2、解决妻子的社保问题。</content><aim>求决</aim><isopen>0</isopen><addorgid>586</addorgid><adduserid>473</adduserid><limitdays>90</limitdays><remark /><Wtsddm>320200</Wtsddm><Djjgdm>320200</Djjgdm><Zdjjbz>0</Zdjjbz><Yybz>0</Yybz><Djjglb>1400</Djjglb></GetInfoResult></GetInfoResponse></soap:Body></soap:Envelope>

//信访人：
/*<visitorname>过仁兴</visitorname>姓名
<idcard>320222195309070979</idcard>身份证
<questionarea>江苏省无锡市无锡新区旺庄街道办事处</questionarea><address>长欣公寓140号201室</address> 详细地址
<mobilephone>15061801896</mobilephone>
<telphone />固定电话*/


//信访件：
//infoid:111721680625 信访编号  ---》诉求编号
//<visittime>2017-08-04T16:42:50</visittime> 信访日期  ---》工单时间
//way:来访  信访方式   ---》诉求方式
//<questiontype>城乡建设_集体土地上房屋拆迁与补偿_安置补偿</questiontype>  内容分类 ----》对应我们的诉求类型？？？？
//source:县系统录入  来源    ---》工单来源
//<aim>求决</aim>信访目的  ---》工单类型
//<isopen>0</isopen> <isopen>0</isopen> ---》是否保密？？还是页面的是否公开
//是否信访件找不到相近意思的字段
//<content>内容 ---》诉求内容
//<limittime>2017-11-02T16:48:24</limittime> 信件超期时间 ----》规定日期
//来源ID找不到相近字段
//处理状态找不到
//业务类型找不到
//附件地址
//<title />标题----》诉求标题
//<Xfcy>信访人因素</Xfcy> 信访原因
//
//<addorg>新吴区旺庄街道办事处</addorg>登记单位

//<isend>0</isend>办理中和已办结  0代表处理中




public class Petitionletter {

}
