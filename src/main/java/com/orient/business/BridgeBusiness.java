package com.orient.business;

import com.orient.bean.AppealBill;
import com.orient.bean.AppealUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * Created by sunweipeng on 2017/7/21.
 */
@Service
public class BridgeBusiness {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //获取当前已经读取了多少条记录
    public int getAreadyReadNum(String identity){
        return jdbcTemplate.query("select records from RECORDS where identity=?", new Object[]{identity}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }

    public void updateReadCount(String system,int totalCount) {
        jdbcTemplate.update("update RECORDS set records = ? where IDENTITY =?",new Object[]{totalCount,system});
    }

    //插入分系统诉求
    public void insertAppeal(AppealBill appealBill) {
        try {
            String sql = "insert into T_FXTSQ_500(ID,C_SQBH_62,C_XDSJ_62,C_GDLY_62,C_YWLX_62,C_SQLX_62,C_SFBM_62,C_SFXFJ_62,C_SQNR_62,C_CLSX_62,C_LYID_62,C_SQZT_62,C_SSPT_62,C_CLZT_62,C_YW_TYPE_62,T_SQYH_500_ID) values(SEQ_T_FXTSQ_500.nextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{appealBill.getC_SQBH_62(), appealBill.getC_XDSJ_62(), appealBill.getC_GDLY_62(), appealBill.getC_YWLX_62(), appealBill.getC_SQLX_62(), appealBill.getC_SFBM_62(), appealBill.getC_SFXFJ_62(), appealBill.getC_SQNR_62(), appealBill.getC_CLSX_62(), appealBill.getC_LYID_62(), appealBill.getC_SQZT_62(), appealBill.getC_SSPT_62(), appealBill.getC_CLZT_62(), appealBill.getC_YW_TYPE_62(), appealBill.getT_SQYH_500_ID()});
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getAppealCount(AppealBill appealBill) {
        String sql = "select * from T_FXTSQ_500 where C_SQBH_62='"+appealBill.getC_SQBH_62()+"'";
        return jdbcTemplate.queryForList(sql).size();
    }
    /**
     * 更新诉求信息，现在要更新的字段主要是处理状态
     * @param appealBill
     */
    public void updateAppeal(AppealBill appealBill) {
        String sql = "update T_FXTSQ_500 set C_CLZT_62=? where C_SQBH_62='"+appealBill.getC_SQBH_62()+"'";
        jdbcTemplate.update(sql,new Object[]{appealBill.getC_CLZT_62()});
    }

    //插入诉求用户
    public void insertUser(AppealUser user) {
        String sql = "insert into T_SQYH_500(ID,C_LXFS_65,C_XXDZ_65,C_XM_65,C_SFZ_65) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{user.getID(),user.getC_LXFS_65(),user.getC_XXDZ_65(),user.getC_XM_65(),user.getC_SFZ_65()});
    }

    public String querySQYHSeqNexVal(){
        String sequence_num = jdbcTemplate.queryForObject("SELECT SEQ_T_SQYH_500.nextVal from dual",String.class);
        return sequence_num;
    }
}
