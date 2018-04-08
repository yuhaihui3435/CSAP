package com.yhh.csap.www.rss;

import cn.hutool.core.util.StrUtil;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.Rss;

import java.util.Date;

public class RssCtr extends CoreController {

    public void list(){
        String status=getPara("status");
        Page page=null;
        if(StrUtil.isNotBlank(status))
            page= Rss.dao.paginate(getPN(),getPS(),"select *  "," from www_rss where status=? and dAt is null",status);
        else
            page=Rss.dao.paginate(getPN(),getPS(),"select *  "," from www_rss where  dAt is null");
        renderJson(page);
    }

    public void save(){
        Rss rss=getModel(Rss.class,"",true);
        rss.setCAt(new Date());
        rss.setOperId(currUser().getId().intValue());
        rss.setStatus(Consts.STATUS.enable.getVal());
        rss.save();
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"rssList");
        renderSuccessJSON("新增广播信息成功");
    }

    public void update(){
        Rss rss=getModel(Rss.class,"",true);
        rss.setOperId(currUser().getId().intValue());
        rss.update();
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"rssList");
        renderSuccessJSON("更新广播信息成功");
    }

    public void del(){
        int id=getParaToInt("id");
        Rss rss=Rss.dao.findById(id);
        rss.setDAt(new Date());
        rss.update();
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"rssList");
        renderSuccessJSON("删除广播信息成功");
    }

    public void expired(){
        int id=getParaToInt("id");
        Rss rss=Rss.dao.findById(id);
        rss.setStatus(Consts.STATUS.expired.getVal());
        rss.update();
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"rssList");
        renderSuccessJSON("广播信息过期设置成功");
    }

    public void view(){
        int id=getParaToInt("id");
        renderJson(Rss.dao.findById(id));
    }

}
