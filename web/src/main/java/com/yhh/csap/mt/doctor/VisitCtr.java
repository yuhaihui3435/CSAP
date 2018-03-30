package com.yhh.csap.mt.doctor;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.mt.DoctorVisit;
import java.util.Date;

public class VisitCtr extends CoreController {

    public void list(){
        String drId=getPara("drId");
        String bDate=getPara("bDate");
        String eDate=getPara("eDate");
        String status=getPara("status");
        Page page=null;
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(drId))
            kv.put("dId=",drId);
        if(StrUtil.isNotBlank(bDate))
            kv.put("visitDate>=",bDate);
        if(StrUtil.isNotBlank(eDate))
            kv.put(" visitDate<=",eDate);
        if(StrUtil.isNotBlank(status))
            kv.put("status=",status);

        SqlPara sqlPara= Db.getSqlPara("doctorVisit.findPage",kv);
        page= DoctorVisit.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(page);
    }

    @Before({Tx.class})
    public void save (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setStatus(Consts.YORN_STR.yes.getVal());
        doctorVisit.setCAt(new Date());
        doctorVisit.save();
        renderSuccessJSON("出诊计划新增成功");
    }

    @Before({Tx.class})
    public void update (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setMAt(new Date());
        doctorVisit.update();
        renderSuccessJSON("出诊计划更新成功");
    }

    @Before({Tx.class})
    public void del (){
        DoctorVisit doctorVisit=getModel(DoctorVisit.class,"",true);
        doctorVisit.setDAt(new Date());
        doctorVisit.update();
        renderSuccessJSON("出诊计划删除成功");
    }

    public void view (){
        int id=getParaToInt("id");
        DoctorVisit doctorVisit= DoctorVisit.dao.findById(id);
        renderJson(doctorVisit);
    }

}
