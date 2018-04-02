package com.yhh.csap.www.at;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.AtMe;

public class AtMeCtr extends CoreController {
    @Before(CacheInterceptor.class)
    @CacheName("atMe")
    public void list(){
        int userId=currUser().getId().intValue();
        Page page= AtMe.dao.paginate(getPN(),getPS(),"select * ","from www_at_me where userId=?",userId);
        renderJson(page);
    }
    @Before(EvictInterceptor.class)
    @CacheName("atMe")
    public void allDel(){
        int userId=currUser().getId().intValue();
        String sql="delete from www_at_me where userId=?";
        Db.delete(sql);
        renderSuccessJSON("@我数据删除成功");
    }
    @Before(EvictInterceptor.class)
    @CacheName("atMe")
    public void del(){
        int id=getParaToInt("id");
        AtMe.dao.deleteById(id);
        renderSuccessJSON("@我数据删除成功");
    }

}
