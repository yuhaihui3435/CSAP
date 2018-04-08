package com.yhh.csap.www;

import com.jfinal.aop.Clear;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Content;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.interceptors.AdminIAuthInterceptor;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.www.model.CarouselSetting;
import com.yhh.csap.www.model.Rss;

import java.util.List;

/**
 * Created by yuhaihui8913 on 2017/12/26.
 */
@Clear(AdminIAuthInterceptor.class)
public class IndexCtr extends CoreController {

    public void index() {
        List clsTop = CarouselSetting.dao.findByArea("index_top");//上部幻灯片
        List clsBottom = CarouselSetting.dao.findByArea("index_bottom");//下部幻灯片
        //关于疾病介绍部分

        List<Content> scienceList = Content.dao.findByTextAndModule(Consts.SECTION.science.name(), "section", true, 3);
        if (!scienceList.isEmpty())
            CacheKit.put(Consts.CACHE_NAMES.index.name(), "scienceList", scienceList);

        setAttr("scienceList", scienceList);
        //名医
        List<DoctorInfo> drList = DoctorInfo.dao.findTop(3);
        //特点案例
        List<Content> successCaseList = Content.dao.findByTextAndModule(Consts.SECTION.successCase.name(), "section", true, 3);
        if (!successCaseList.isEmpty())
            CacheKit.put(Consts.CACHE_NAMES.index.name(), "successCaseList", successCaseList);
        setAttr("successCaseList", successCaseList);
        //rss
        List<Rss> rssList=Rss.dao.findByCache(Consts.CACHE_NAMES.index.name(),"rssList","select * from www_rss where status='0' and dAt is null ");
        setAttr("rssList",rssList);

        //友情了链接
        setAttr("fsLinkList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "fsLinkList"));
        setAttr("drList", drList);
        setAttr("clsTopList", clsTop);
        setAttr("clsBottomList", clsBottom);
        render("index.html");
    }

}
