package com.yhh.csap.www.rss;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.Consts;
import com.yhh.csap.www.model.Rss;

import java.util.Date;
import java.util.List;

public class RssSrv {


    @Before(Tx.class)
    public void updateRssExpired(List<Rss> rsses){
        for (Rss rss:rsses){
            rss.setStatus(Consts.STATUS.expired.getVal());
            rss.update();
        }
    }

    public List<Rss> findByExpiredAt(Date date){
        if(date==null)date=new Date();
        String sql="select * from www_rss where status='0' and dAt is null and expiredAt=?";
        return Rss.dao.find(sql,date);
    }

}
