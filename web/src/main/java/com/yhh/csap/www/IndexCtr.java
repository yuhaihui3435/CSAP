package com.yhh.csap.www;

import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Content;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.interceptors.AdminIAuthInterceptor;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.www.model.CarouselSetting;

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

        setAttr("fsLinkList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "fsLinkList"));
        setAttr("drList", drList);
        setAttr("clsTopList", clsTop);
        setAttr("clsBottomList", clsBottom);
        render("index.html");
    }


    public void science(){
        String searchKey=getPara("searchKey");
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.science.name(),Consts._SECTION,searchKey);
        setAttr("page",page);
        render(Consts.SECTION.science.name().concat("/main.html"));
    }

    public void successCase(){
        String searchKey=getPara("searchKey");
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.successCase.name(),Consts._SECTION,searchKey);
        setAttr("page",page);
        render(Consts.SECTION.successCase.name().concat("/main.html"));
    }

    public void dr(){

    }

    public void communicate(){

    }

    public void about(){

    }







}
