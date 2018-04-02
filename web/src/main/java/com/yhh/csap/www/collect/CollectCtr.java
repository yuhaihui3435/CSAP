package com.yhh.csap.www.collect;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.Collect;

import java.util.Date;

public class CollectCtr extends CoreController {
    @Before(CacheInterceptor.class)
    @CacheName("collect")
    public void list(){
        int userId=currUser().getId().intValue();
        Page page= Collect.dao.paginate(getPN(),getPS(),"select * ","from www_collect where userId=?",userId);
        renderJson(page);
    }
    @Before(EvictInterceptor.class)
    @CacheName("collect")
    public void save(){
        Collect collect=getModel(Collect.class,"",true);
        collect.setCAt(new Date());
        collect.save();
        renderSuccessJSON("收藏成功");
    }
    @Before(EvictInterceptor.class)
    @CacheName("collect")
    public void del(){
        int id=getParaToInt("id");
        Collect.dao.deleteById(id);
        renderSuccessJSON("取消收藏成功");
    }





}
