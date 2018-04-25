package com.yhh.csap.www;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Content;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.interceptors.AdminIAuthInterceptor;
import com.yhh.csap.interceptors.WwwHelperInterceptor;
import com.yhh.csap.interceptors.WwwwSidebarInterceptor;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.mt.DoctorTax;
import com.yhh.csap.www.like.LikeRecordsSrv;
import com.yhh.csap.www.model.CarouselSetting;
import com.yhh.csap.www.model.LikeRecords;
import com.yhh.csap.www.model.Replys;
import com.yhh.csap.www.model.Rss;

import java.util.List;

/**
 * Created by yuhaihui8913 on 2017/12/26.
 */
//@Clear(AdminIAuthInterceptor.class)
public class IndexCtr extends CoreController {

    private LikeRecordsSrv likeRecordsSrv=enhance(LikeRecordsSrv.class.getCanonicalName(),LikeRecordsSrv.class);

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

    @Before({WwwwSidebarInterceptor.class})
    public void science(){
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.science.name(),Consts._SECTION,null);
        setAttr("page",page);
        render(Consts.SECTION.science.name().concat("/main.html"));
    }
    @Before({WwwHelperInterceptor.class,WwwwSidebarInterceptor.class})
    public void scienceView(){
        int id=getParaToInt(0);
        Db.update("update s_content set viewCount=viewCount+1 where id=?",id);
        Content content=Content.dao.findById(id);
        setAttr("art",content);
        setAttr("currSectionName",getAttrForStr("currTitle"));
        setAttr("currTitle",content.getTitle());
        setAttr("currSection",Consts.SECTION.science.name());
        setAttr("targetObjName","content");
        setAttr("targetObjId",content.getId());
        setAttr("laudCount",content.getLaudCount());
        setAttr("collectCount",content.getCollectCount());
        setAttr("pageTitle",content.getTitle());
        render(Consts.SECTION.science.name().concat("/view.html"));
    }
    @Before({WwwwSidebarInterceptor.class})
    public void successCase(){
        Page<Content> page=Content.dao.pageByTextAndModule(getPN(),getPS(),Consts.SECTION.successCase.name(),Consts._SECTION,null);
        setAttr("page",page);
        render(Consts.SECTION.successCase.name().concat("/main.html"));
    }
    @Before({WwwHelperInterceptor.class,WwwwSidebarInterceptor.class})
    public void successCaseView(){
        int id=getParaToInt(0);
        Db.update("update s_content set viewCount=viewCount+1 where id=?",id);
        Content content=Content.dao.findById(id);
        setAttr("art",content);
        setAttr("currSectionName",getAttrForStr("currTitle"));
        setAttr("currTitle",content.getTitle());
        setAttr("currSection",Consts.SECTION.science.name());
        setAttr("targetObjName","content");
        setAttr("targetObjId",content.getId());
        setAttr("laudCount",content.getLaudCount());
        setAttr("collectCount",content.getCollectCount());
        setAttr("pageTitle",content.getTitle());
        render(Consts.SECTION.science.name().concat("/view.html"));
    }
    @Before({WwwwSidebarInterceptor.class})
    public void dr(){
        Page<DoctorInfo> page= DoctorInfo.dao.paginate(getPN(),getPS(),"select * ","from mt_doctor_info where dAt is null and status='0'");
        setAttr("page",page);
        render(Consts.SECTION.dr.name().concat("/main.html"));
    }
    @Before({WwwHelperInterceptor.class,WwwwSidebarInterceptor.class})
    public void drView(){
        int id=getParaToInt(0);
        Db.update("update mt_doctor_info set viewCount=viewCount+1 where id=?",id);
        DoctorInfo doctorInfo=DoctorInfo.dao.findById(id);
        setAttr("dr",doctorInfo);
        setAttr("currSectionName",getAttrForStr("currTitle"));
        setAttr("currTitle",doctorInfo.getName());
        setAttr("currSection",Consts.SECTION.dr.name());
        setAttr("targetObjName","dr");
        setAttr("targetObjId",doctorInfo.getId());
        setAttr("laudCount",doctorInfo.getLaudCount());
        setAttr("collectCount",doctorInfo.getCollectCount());
        setAttr("pageTitle","医生："+doctorInfo.getName());
        render(Consts.SECTION.dr.name().concat("/view.html"));
    }

    public void communicate(){
        setAttr("plateList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"plateList"));
        render(Consts.SECTION.communicate.name().concat("/main.html"));
    }

    public void about(){

    }

    /**
     *
     * 全局搜索
     *
     */
    public void globalSearch(){

    }


    /**
     *
     * 点赞
     *
     */
    public void thumbUp(){
        LikeRecords likeRecords=getModel(LikeRecords.class,"",true);
        likeRecords.setUserId(currUser().getId().intValue());
        likeRecordsSrv.LikeAddSave(likeRecords);
        long laudCount=Db.queryLong("select laudCount from "+likeRecords.getTargetObj() +" where id=?",likeRecords.getTargetId());
        renderSuccessJSON("点赞成功",String.valueOf(laudCount));
    }

    /**
     * 点赞取消
     *
     */
    public void thumbDown(){
        LikeRecords likeRecords=getModel(LikeRecords.class,"",true);
        likeRecords.setUserId(currUser().getId().intValue());
        likeRecordsSrv.LikeSubSave(likeRecords);
        long laudCount=Db.queryLong("select laudCount from "+likeRecords.getTargetObj() +" where id=?",likeRecords.getTargetId());
        CacheKit.remove(Consts.CACHE_NAMES.hold.name(),"likeRecords_".concat(likeRecords.getUserId().toString()).concat(StrUtil.UNDERLINE).concat(likeRecords.getTargetObj()).concat(StrUtil.UNDERLINE).concat(likeRecords.getTargetId().toString()));
        renderSuccessJSON("点赞成功",String.valueOf(laudCount));
    }



}
