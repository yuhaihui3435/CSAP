package com.yhh.csap.mt.doctor;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.mt.DoctorInfo;

import javax.print.Doc;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.doctor]
 * 类名称:    [DoctorCtr]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/26]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class DoctorCtr extends CoreController {

    public void dr(){
        //疾病列表
        List<Taxonomy> diseaseList= CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"diseaseList");
        //手术方式
        List<Taxonomy> opModelList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"opModelList");
        //职称
        List<Taxonomy> drTitleList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"drTitleList");

        Map<String,List<Taxonomy>> map=new HashMap<>();

        map.put("diseaseList",diseaseList);
        map.put("opModelList",opModelList);
        map.put("drTitleList",drTitleList);

        renderJson(map);

    }


    public void list(){
        String name=getPara("name");
        String email=getPara("email");
        String sex=getPara("sex");
        String tel=getPara("tel");
        Page page=null;
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(name))
            kv.put("instr(di.name,",name+")>0");
        if(StrUtil.isNotBlank(email))
            kv.put(" instr(di.email,",email+")>0");
        if(StrUtil.isNotBlank(sex))
            kv.put(" di.sex=",sex);
        if(StrUtil.isNotBlank(tel))
            kv.put("tel",tel);

        SqlPara sqlPara= Db.getSqlPara("doctor.findPage",kv);
        page=DoctorInfo.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(page);
    }

    public void save(){
        DoctorInfo doctorInfo=getModel(DoctorInfo.class,"",true);

        doctorInfo.setCAt(new Date());
        doctorInfo.setOperId(currUser()==null?null:currUser().getId().intValue());
        User user=new User();
        user.setCAt(new Date());


    }

}
