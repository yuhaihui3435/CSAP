package com.yhh.csap.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Content;

import java.util.List;

public class WwwwSidebarInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
        List<Content> top5List=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.science.name(),Consts._SECTION,true,5,new String[]{"laudCount"});
        List<Content> top5List_successCase=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.successCase.name(),Consts._SECTION,true,5,new String[]{"laudCount"});
        inv.getController().setAttr("top5List_science",top5List);
        inv.getController().setAttr("top5List_successCase",top5List_successCase);
        inv.getController().setAttr("tagList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),Consts._TAG.concat("List")));
        inv.getController().setAttr("queryAction", inv.getActionKey());

    }
}
