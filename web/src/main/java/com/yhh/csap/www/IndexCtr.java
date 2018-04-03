package com.yhh.csap.www;

import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Content;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.interceptors.AdminIAuthInterceptor;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.www.model.CarouselSetting;

import java.util.*;

/**
 * Created by yuhaihui8913 on 2017/12/26.
 */
@Clear(AdminIAuthInterceptor.class)
public class IndexCtr extends CoreController {

    public void index() {
        List clsTop=CarouselSetting.dao.findByArea("index_top");//上部幻灯片
        List clsBottom=CarouselSetting.dao.findByArea("index_bottom");//下部幻灯片
        //关于疾病介绍部分
        if(CacheKit.get(Consts.CACHE_NAMES.index.name(),"communicateList")==null) {
            List<Content> communicateList = Content.dao.findByTextAndModule(Consts.SECTION.communicate.name(), "section", true, 3);
            CacheKit.put(Consts.CACHE_NAMES.index.name(), "communicateList", communicateList);
            setAttr("communicateList",communicateList);
        }

        List<DoctorInfo> drList=DoctorInfo.dao.findTop(3);



        setAttr("drList",drList);
        setAttr("clsTopList",clsTop);
        setAttr("clsBottomList",clsBottom);
        render("index.html");
    }

}
