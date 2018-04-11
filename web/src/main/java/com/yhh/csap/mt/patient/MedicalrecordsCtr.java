package com.yhh.csap.mt.patient;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.yhh.csap.mt.UserMedicalrecords;
import com.yhh.csap.mt.UserMedicalrecordsTax;
import com.yhh.csap.mt.doctor.DoctorSrv;

import java.util.*;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.patient]
 * 类名称:    [MedicalrecordsCtr]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/31]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class MedicalrecordsCtr extends CoreController {
    private DoctorSrv doctorSrv=enhance(DoctorSrv.class.getSimpleName(),DoctorSrv.class);
    private MedicalrecordsSrv medicalrecordsSrv=enhance(MedicalrecordsSrv.class.getSimpleName(),MedicalrecordsSrv.class);

    public void dr(){
        List<DoctorInfo> doctorInfoList=doctorSrv.findByNameLike(Consts.BLANK);
        Map<String,Object> map=new HashMap<>();
        map.put("drList",doctorInfoList);
        map.put("symptomList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"symptom".concat("List")));//病症
        map.put("diseaseList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"disease".concat("List")));//病情
        map.put("opModelList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"opModel".concat("List")));//手术方式
        map.put("treatEffectList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"treatEffect".concat("List")));//治疗效果
        map.put("hospList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"visitHospList"));
        renderJson(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect));
    }
    @Before({Tx.class})
    public void save(){
        UserMedicalrecords userMedicalrecords=getModel(UserMedicalrecords.class,"",true);
        String symptoms=getPara("symptoms");
        String diseases=getPara("deseases");
        String opModes=getPara("opModes");
        String bOpSymptoms=getPara("bOpSymptoms");
        String afOpSymptoms=getPara("afOpSymptoms");
        String treatEffect=getPara("treatEffect");
        String doctorIds=getPara("drIds");
        userMedicalrecords.setUserId(currUser().getId().intValue());
        userMedicalrecords.setCAt(new Date());
        userMedicalrecords.save();
        Integer id=null;
        List<UserMedicalrecordsTax> userMedicalrecordsTaxes=new ArrayList<>();
        UserMedicalrecordsTax userMedicalrecordsTax=null;

        //1:治疗效果，2手术方式，3术前症状，4术后症状，5病情，6病症 保存处理
        String[] strings=diseases.split(",");
        medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.deseases);
        if(userMedicalrecords.getIfCure().equals(Consts.YORN_STR.yes.getVal())){
            strings=opModes.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.opModes);
            strings=bOpSymptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.bOpSymptoms);
            strings=afOpSymptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.afOpSymptoms);
            medicalrecordsSrv.addMedicalrecordsTax(new String[]{treatEffect},userMedicalrecords.getId(), UserMedicalrecords.TaxType.treatEffect);
        }else{
            strings=symptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.symptoms);
        }


        //治疗医生保存
        if(StrUtil.isNotBlank(doctorIds)){
            strings=doctorIds.split(",");
            medicalrecordsSrv.addMedicalrecordsDoctor(strings,userMedicalrecords.getId());
        }
        CacheKit.remove(Consts.CACHE_NAMES.userMedicalrecords.name(),"byUser_"+userMedicalrecords.getUserId());
        renderSuccessJSON("医疗档案创建成功");

    }

    @Before({Tx.class})
    public void update(){
        UserMedicalrecords userMedicalrecords=getModel(UserMedicalrecords.class,"",true);
        String symptoms=getPara("symptoms");
        String diseases=getPara("diseases");
        String opModes=getPara("opModes");
        String bOpSymptoms=getPara("bOpSymptoms");
        String afOpSymptoms=getPara("afOpSymptoms");
        String treatEffect=getPara("treatEffect");
        String doctorIds=getPara("drIds");

        userMedicalrecords.setMAt(new Date());
        userMedicalrecords.update();

        medicalrecordsSrv.delMedicalrecordsTaxByUmId(userMedicalrecords.getId());
        medicalrecordsSrv.delMedicalrecordsTaxByUmId(userMedicalrecords.getId());

        //1:治疗效果，2手术方式，3术前症状，4术后症状，5病情，6病症 保存处理
        String[] strings=diseases.split(",");
        medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.deseases);
        if(userMedicalrecords.getIfCure().equals(Consts.YORN_STR.yes.getVal())){
            strings=opModes.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.opModes);
            strings=bOpSymptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.bOpSymptoms);
            strings=afOpSymptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.afOpSymptoms);
            medicalrecordsSrv.addMedicalrecordsTax(new String[]{treatEffect},userMedicalrecords.getId(), UserMedicalrecords.TaxType.treatEffect);
        }else{
            strings=symptoms.split(",");
            medicalrecordsSrv.addMedicalrecordsTax(strings,userMedicalrecords.getId(), UserMedicalrecords.TaxType.symptoms);
        }


        //治疗医生保存
        if(StrUtil.isNotBlank(doctorIds)){
            strings=doctorIds.split(",");
            medicalrecordsSrv.addMedicalrecordsDoctor(strings,userMedicalrecords.getId());
        }
        CacheKit.remove(Consts.CACHE_NAMES.userMedicalrecords.name(),"byUser_"+userMedicalrecords.getUserId());
        CacheKit.remove(Consts.CACHE_NAMES.userMedicalrecords.name(),"view_"+userMedicalrecords.getId());

        renderSuccessJSON("医疗档案更新成功");
    }

    public void del(){
        int umId=getParaToInt("umId");
        UserMedicalrecords userMedicalrecords=UserMedicalrecords.dao.findById(umId);
        userMedicalrecords.setDAt(new Date());
        userMedicalrecords.update();
        CacheKit.remove(Consts.CACHE_NAMES.userMedicalrecords.name(),"byUser_"+userMedicalrecords.getUserId());
        CacheKit.remove(Consts.CACHE_NAMES.userMedicalrecords.name(),"view_"+userMedicalrecords.getId());
        renderSuccessJSON("医疗档案删除成功");
    }


    public void list(){
        int userId=currUser().getId().intValue();
        renderJson(UserMedicalrecords.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"byUser_"+userId,"select * from mt_user_medicalrecords where dAt is null and userId=?",userId));
    }

    public void query(){
        String symptoms=getPara("symptoms");
        String diseases=getPara("diseases");
        String opModels=getPara("opModels");
        String treatEffect=getPara("treatEffect");
        String doctorIds=getPara("drIds");
        String hosp=getPara("hosp");
        String ifCure=getPara("ifCure");

        StringBuilder tax=new StringBuilder();
        tax.append(symptoms).append(",").append(diseases).append(",").append(opModels).append(",").append(treatEffect);
        StringBuilder dr=new StringBuilder();
        dr.append(doctorIds);

        Kv kv= Kv.create();
        kv.put("tax",tax.toString());
        kv.put("dr",dr.toString());
        if(StrUtil.isNotBlank(hosp))
            kv.put("hosp=",hosp);
        if(StrUtil.isNotBlank(ifCure))
            kv.put("ifCure=",ifCure);

        SqlPara sqlPara= Db.getSqlPara("userMedicalrecords.queryUmByCdn",Kv.by("cond",kv));
        Page page= DoctorVisit.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(JSON.toJSONString(page,SerializerFeature.DisableCircularReferenceDetect));
    }

    public void view(){
       int umId=getParaToInt("umId");
       UserMedicalrecords userMedicalrecords=UserMedicalrecords.dao.findFirstByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"view_"+umId,"select * from mt_user_medicalrecords where id=?",umId);
       renderJson(JSON.toJSONString(userMedicalrecords,SerializerFeature.DisableCircularReferenceDetect));
    }

}
