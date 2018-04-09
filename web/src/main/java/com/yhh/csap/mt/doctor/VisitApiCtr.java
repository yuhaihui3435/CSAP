package com.yhh.csap.mt.doctor;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.mt.DoctorVisitApi;

import java.util.List;

public class VisitApiCtr extends CoreController {

    public void list(){
        String drId=getPara("drId");
        List<DoctorVisitApi> list= DoctorVisitApi.dao.findByCache(Consts.CACHE_NAMES.doctorVisit.name(),"visitApi_"+drId,"select * from mt_doctor_visit_api where drId=?",drId);
        renderJson(list);
    }


    public void save (){
        DoctorVisitApi doctorVisitApi=getModel(DoctorVisitApi.class,"",true);
        doctorVisitApi.save();
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitApi_"+doctorVisitApi.getDrId());
        renderSuccessJSON("挂号服务网址保存成功");
    }


    public void update (){
        DoctorVisitApi doctorVisitApi=getModel(DoctorVisitApi.class,"",true);
        doctorVisitApi.update();
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitApi_"+doctorVisitApi.getDrId());
        renderSuccessJSON("挂号服务网址更新成功");
    }


    public void del (){
        int dvaId=getParaToInt("id");
        DoctorVisitApi doctorVisitApi=DoctorVisitApi.dao.findById(dvaId);
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitApi_"+doctorVisitApi.getDrId());
        doctorVisitApi.delete();
        renderSuccessJSON("挂号服务网址删除成功");
    }
}
