package com.yhh.csap.mt.doctor;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.mt.DoctorVisit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VisitCtr extends CoreController {

    public void list(){
        String dId=getPara("dId");
        String bDate=getPara("bDate");
        String eDate=getPara("eDate");
        String status=getPara("status");
        String address=getPara("address");
        Page page=null;
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(dId))
            kv.put("dv.dId=",dId);
        if(StrUtil.isNotBlank(bDate))
            kv.put("dv.visitDate>=",bDate);
        if(StrUtil.isNotBlank(eDate))
            kv.put(" dv.visitDate<=",eDate);
        if(StrUtil.isNotBlank(status))
            kv.put("dv.status=",status);
        if(StrUtil.isNotBlank(address))
            kv.put("dv.address=",address);

        SqlPara sqlPara= Db.getSqlPara("doctorVisit.findPage",Kv.by("cond",kv));
        page= DoctorVisit.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(page);

    }

    @Before({VisitValidate.class,Tx.class})
    public void save (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setStatus(Consts.YORN_STR.yes.getVal());
        doctorVisit.setCAt(new Date());
        doctorVisit.setOperId(currUser()!=null?currUser().getId().intValue():null);
        doctorVisit.save();
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitByDrAndLimitRecent_"+doctorVisit.getDId());
        renderSuccessJSON("出诊计划新增成功");
    }

    @Before({Tx.class})
    public void update (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setMAt(new Date());
        doctorVisit.update();
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitByDrAndLimitRecent_"+doctorVisit.getDId());
        renderSuccessJSON("出诊计划更新成功");
    }

    @Before({Tx.class})
    public void del (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setDAt(new Date());
        doctorVisit.update();
        CacheKit.remove(Consts.CACHE_NAMES.doctorVisit.name(),"visitByDrAndLimitRecent_"+doctorVisit.getDId());
        renderSuccessJSON("出诊计划删除成功");
    }

    public void view (){
        int id=getParaToInt("id");
        DoctorVisit doctorVisit= DoctorVisit.dao.findById(id);
        renderJson(doctorVisit);
    }

    public void dr(){
        Map<String,Object> map=new HashMap<>();
        map.put("drList", DoctorInfo.dao.findByPropEQWithDat("status",Consts.STATUS.enable.getVal()));
        map.put("hospList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"visitHospList"));
        renderJson(map);
    }

}
