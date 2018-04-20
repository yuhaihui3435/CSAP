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
import com.yhh.csap.mt.DoctorTax;
import com.yhh.csap.www.model.CarouselSetting;
import com.yhh.csap.www.model.Replys;
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

        List<Content> scienceList=CacheKit.get(Consts.CACHE_NAMES.index.name(),"scienceList");
        if (scienceList==null||scienceList.isEmpty()){
            scienceList = Content.dao.findByTextAndModule(Consts.SECTION.science.name(), "section", true, 3);
            CacheKit.put(Consts.CACHE_NAMES.index.name(), "scienceList", scienceList);
        }
        setAttr("scienceList", scienceList);
        //名医
        List<DoctorInfo> drList = DoctorInfo.dao.findTop(4);
        //特点案例
        List<Content> successCaseList =CacheKit.get(Consts.CACHE_NAMES.index.name(),"successCaseList");
        if (successCaseList==null||successCaseList.isEmpty()){
            successCaseList=Content.dao.findByTextAndModule(Consts.SECTION.successCase.name(), "section", true, 3);
            CacheKit.put(Consts.CACHE_NAMES.index.name(), "successCaseList", successCaseList);
        }
        setAttr("successCaseList", successCaseList);

        setAttr("drList", drList);
        setAttr("clsTopList", clsTop);
        setAttr("clsBottomList", clsBottom);
        render("index.html");
    }


    public void science(){
        String searchKey=getPara("searchKey");
        keepPara("searchKey");
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.science.name(),Consts._SECTION,searchKey);
        List<Content> top5List=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.science.name(),Consts._SECTION,true,5,new String[]{"viewCount"});

        setAttr("page",page);
        setAttr("top5List",top5List);
        setAttr("tagList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),Consts._TAG.concat("List")));
        render(Consts.SECTION.science.name().concat("/main.html"));
    }

    public void scienceView(){
        int id=getParaToInt(0);
        Content content=Content.dao.findById(id);
        setAttr("art",content);
        List<Content> top5List=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.science.name(),Consts._SECTION,true,5,new String[]{"viewCount"});
        setAttr("top5List",top5List);
        setAttr("currSectionName",getAttrForStr("currTitle"));
        setAttr("currTitle",content.getTitle());
        setAttr("currSection",Consts.SECTION.science.name());
        setAttr("replyObjName","content");
        setAttr("tagList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),Consts._TAG.concat("List")));
        render(Consts.SECTION.science.name().concat("/view.html"));
    }

    public void successCase(){
        String searchKey=getPara("searchKey");
        keepPara("searchKey");
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.successCase.name(),Consts._SECTION,searchKey);
        List<Content> top5List=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.successCase.name(),Consts._SECTION,true,5,new String[]{"viewCount"});
        setAttr("page",page);
        setAttr("top5List",top5List);
        setAttr("tagList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),Consts._TAG.concat("List")));
        render(Consts.SECTION.successCase.name().concat("/main.html"));
    }

    public void dr(){
        Page<DoctorInfo> page= DoctorInfo.dao.paginate(getPN(),getPS(),"select * ","from mt_doctor_info where dAt is null and status='0'");
        List<Content> top5List=Content.dao.findByTextAndModuleAndOrderby(Consts.SECTION.science.name(),Consts._SECTION,true,5,new String[]{"viewCount"});
        setAttr("page",page);
        setAttr("top5List",top5List);
        setAttr("tagList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),Consts._TAG.concat("List")));
        render(Consts.SECTION.dr.name().concat("/main.html"));
    }

    public void communicate(){

    }

    public void about(){

    }


    public void addReply(){
        Replys replys=getModel(Replys.class,"",true);

    }




}
