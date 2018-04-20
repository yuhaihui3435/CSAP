package com.yhh.csap.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.Rss;

import java.util.List;

/**
 * Created by yuhaihui8913 on 2017/12/26.
 */

public class WwwInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        CoreController controller = (CoreController)invocation.getController();
        String ck=invocation.getControllerKey();
        String ak=invocation.getActionKey();
        //获取系统设置的参数
        List<String> list=CacheKit.getKeys(Consts.CACHE_NAMES.paramCache.name());
        for (String str:list){
            controller.setAttr(str,(String)CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),str));
        }
        //获取菜单
        List menuList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"sectionList");
        controller.setAttr("menuList",menuList);
        //当前路径，处理点击过的菜单激活问题
        controller.setAttr("currPath",ak);
        //栏目的title的获取
        Taxonomy taxonomy=getSectionIdFromCacheByText(ak.replaceAll("/","").replaceAll("View",""));
        controller.setAttr("currTitle",taxonomy==null?"":taxonomy.getTitle());
        //rss
        List<Rss> rssList=Rss.dao.findByCache(Consts.CACHE_NAMES.index.name(),"rssList","select * from www_rss where status='0' and dAt is null ");
        controller.setAttr("rssList",rssList);

        //友情了链接
        controller.setAttr("fsLinkList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "fsLinkList"));
        invocation.invoke();


    }
    //根据栏目的标识符号 查找缓存中的栏目数据
    private Taxonomy getSectionIdFromCacheByText(String txt){
        List<Taxonomy> taxonomies=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"sectionList");
        for (Taxonomy taxonomy:taxonomies){
            if(taxonomy.getText().equals(txt)){
                return taxonomy;
            }
        }
        return null;
    }

}
