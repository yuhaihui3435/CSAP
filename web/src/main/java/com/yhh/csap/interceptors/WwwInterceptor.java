package com.yhh.csap.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;

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
        List<String> list=CacheKit.getKeys(Consts.CACHE_NAMES.paramCache.name());
        for (String str:list){
            controller.setAttr(str,(String)CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),str));
        }

        List menuList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"artList");
        controller.setAttr("menuList",menuList);

        if(ak.equals("/")){
            controller.setAttr("currId","-1");
        }else if (ak.equals("/a")||ak.equals("/b")||ak.equals("/c")||ak.equals("/d")){
            controller.setAttr("currId",controller.getPara(0));
        }

        invocation.invoke();


    }
}
