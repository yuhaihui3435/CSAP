package com.yhh.csap.www.fan;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.Fans;
import org.apache.poi.sl.draw.binding.CTAdjustHandleList;

import java.util.Date;

public class FanCtr extends CoreController {

    /**
     * 关注列表
     */
    public void list(){
        int userId=currUser().getId().intValue();
        Page page=Fans.dao.paginate(getPN(),getPS(),"select * ","from www_fans where fanId=?",userId);
        renderJson(page);
    }

    /**
     * 统计fans数量
     *
     */
    public void countFans(){
        int userId=currUser().getId().intValue();
        long count=Db.queryLong("select count(*) as count from www_fans where followId=?",userId);
        renderJson("fansCount",count);
    }

    public void save(){
        int fanId=getParaToInt("fanId");
        int followId=getParaToInt("followId");
        Fans fans=new Fans();
        fans.setFanId(fanId);
        fans.setFollowId(followId);
        fans.setFanAt(new Date());
        fans.save();
        renderSuccessJSON("关注成功");
    }


    public void del(){
        int id=getParaToInt("id");
        Fans.dao.deleteById(id);
        renderSuccessJSON("取消关注成功");
    }

}
