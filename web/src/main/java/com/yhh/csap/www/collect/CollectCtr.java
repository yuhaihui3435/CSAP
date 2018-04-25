package com.yhh.csap.www.collect;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.Collect;

import java.util.Date;
import java.util.List;

public class CollectCtr extends CoreController {

    public void list(){
        int userId=currUser().getId().intValue();
        Page page= Collect.dao.paginate(getPN(),getPS(),"select * ","from www_collect where userId=?",userId);
        renderJson(page);
    }
    public void save(){
        Collect collect=getModel(Collect.class,"",true);
        collect.setUserId(currUser().getId().intValue());
        collect.setTargetObj(Consts.MAPPING_TO_TBL.get(collect.getTargetObj()));
        List list=Collect.dao.find("select * from www_collect where targetId=? and targetObj=? and userId=?",collect.getTargetId(),collect.getTargetObj(),currUser().getId());
        if(list.isEmpty()) {
            collect.setCAt(new Date());
            collect.save();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update ").append(collect.getTargetObj()).append(" set collectCount=collectCount+1 where id=?");
            Db.update(stringBuilder.toString(), collect.getTargetId());
        }
        long count=Db.queryLong("select collectCount from "+collect.getTargetObj() +" where id=?",collect.getTargetId());
        renderSuccessJSON("收藏成功",String.valueOf(count));
    }
    public void del(){
        Collect collect=getModel(Collect.class,"",true);
        collect.setTargetObj(Consts.MAPPING_TO_TBL.get(collect.getTargetObj()));
        Db.delete("delete from www_collect where targetId=? and targetObj=? and userId=?",collect.getTargetId(),collect.getTargetObj(),currUser().getId());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(collect.getTargetObj()).append(" set collectCount=collectCount-1 where id=?");
        Db.update(stringBuilder.toString(),collect.getTargetId());
        long count=Db.queryLong("select collectCount from "+collect.getTargetObj() +" where id=?",collect.getTargetId());
        CacheKit.remove(Consts.CACHE_NAMES.hold.name(),"collect_".concat(currUser().getId().toString()).concat(StrUtil.UNDERLINE).concat(collect.getTargetObj()).concat(StrUtil.UNDERLINE).concat(collect.getTargetId().toString()));
        renderSuccessJSON("取消收藏成功",String.valueOf(count));
    }





}
