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
import com.yhh.csap.mt.*;
import com.yhh.csap.mt.doctor.DoctorSrv;

import java.util.*;

public class OpsCtr extends CoreController {

    private OpsSrv opsSrv = enhance(OpsSrv.class.getSimpleName(), OpsSrv.class);
    private DoctorSrv doctorSrv = enhance(DoctorSrv.class.getSimpleName(), DoctorSrv.class);

    public void dr() {
        List<DoctorInfo> doctorInfoList = doctorSrv.findByNameLike(Consts.BLANK);
        Map<String, Object> map = new HashMap<>();
        map.put("drList", doctorInfoList);
        map.put("opModeList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "opMode".concat("List")));//手术方式
        map.put("treatEffectList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "treatEffect".concat("List")));//治疗效果
        map.put("hospList", CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(), "visitHospList"));
        renderJson(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Before({Tx.class})
    public void save() {
        UserOps userOps = getModel(UserOps.class, "", true);
        String opModes = getPara("opModes");
        String bOpSymptoms = getPara("bOpSymptoms");
        String afOpSymptoms = getPara("afOpSymptoms");
        String doctorIds = getPara("drIds");
        userOps.setUserId(currUser().getId().intValue());
        userOps.setCAt(new Date());
        userOps.save();
        Integer id = null;
        List<UserOpsTax> userOpsTaxes = new ArrayList<>();
        UserOpsTax userOpsTax = null;


        String[] strings = opModes.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.opModes);
        strings = bOpSymptoms.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.bOpSymptoms);
        strings = afOpSymptoms.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.afOpSymptoms);


        //治疗医生保存
        if (StrUtil.isNotBlank(doctorIds)) {
            strings = doctorIds.split(",");
            opsSrv.addOpsDoctor(strings, userOps.getId());
        }
        CacheKit.remove(Consts.CACHE_NAMES.userOps.name(), "byUser_" + userOps.getUserId());
        renderSuccessJSON("手术记录创建成功");

    }


    @Before({Tx.class})
    public void update() {
        UserOps userOps = getModel(UserOps.class, "", true);
        String opModes = getPara("opModes");
        String bOpSymptoms = getPara("bOpSymptoms");
        String afOpSymptoms = getPara("afOpSymptoms");
        String doctorIds = getPara("drIds");
        userOps.setMAt(new Date());
        userOps.update();
        opsSrv.delMedicalrecordsTaxByUmId(userOps.getId());
        opsSrv.delMeicalrecordsDoctorByUmId(userOps.getId());
        //1:治疗效果，2手术方式，3术前症状，4术后症状， 保存处理
        String[] strings = opModes.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.opModes);
        strings = bOpSymptoms.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.bOpSymptoms);
        strings = afOpSymptoms.split(",");
        opsSrv.addOpsTax(strings, userOps.getId(), UserMedicalrecords.TaxType.afOpSymptoms);
//        治疗医生保存
        if (StrUtil.isNotBlank(doctorIds)) {
            strings = doctorIds.split(",");
            opsSrv.addOpsDoctor(strings, userOps.getId());
        }
        CacheKit.remove(Consts.CACHE_NAMES.userOps.name(), "byUser_" + userOps.getUserId());
        CacheKit.remove(Consts.CACHE_NAMES.userOps.name(), "view_" + userOps.getId());
        renderSuccessJSON("手术记录更新成功");
    }

    public void del() {
        int uoId = getParaToInt("id");
        UserOps userOps = UserOps.dao.findById(uoId);
        userOps.setDAt(new Date());
        userOps.update();
        CacheKit.remove(Consts.CACHE_NAMES.userOps.name(), "byUser_" + userOps.getUserId());
        CacheKit.remove(Consts.CACHE_NAMES.userOps.name(), "view_" + userOps.getId());
        renderSuccessJSON("手术记录删除成功");
    }


    public void list() {
        int userId = currUser().getId().intValue();
        renderJson(UserOps.dao.findByCache(Consts.CACHE_NAMES.userOps.name(), "byUser_" + userId, "select * from mt_user_ops where dAt is null and userId=?", userId));
    }

    public void query() {
        String opModes = getPara("opModes");
        String treatEffect = getPara("treatEffect");
        String bOpSymptoms = getPara("bOpSymptoms");
        String afOpSymptoms = getPara("afOpSymptoms");
        String doctorIds = getPara("drIds");
        String hosp = getPara("hosp");

        StringBuilder tax = new StringBuilder();

        if (StrUtil.isNotBlank(opModes))
            tax.append(opModes);
        if (StrUtil.isNotBlank(bOpSymptoms))
            if (tax.length() > 0)
                tax.append(",").append(bOpSymptoms);
            else
                tax.append(bOpSymptoms);
        if (StrUtil.isNotBlank(afOpSymptoms)) {
            if (tax.length() > 0)
                tax.append(",").append(afOpSymptoms);
            else
                tax.append(afOpSymptoms);
        }
        StringBuilder dr = new StringBuilder();
        dr.append(doctorIds);

        Kv kv = Kv.create();
        kv.put("tax", tax.toString());
        kv.put("dr", dr.toString());
        if (StrUtil.isNotBlank(hosp))
            kv.put("hosp=", hosp);
        if (StrUtil.isNotBlank(treatEffect))
            kv.put("tmEftId=", treatEffect);

        SqlPara sqlPara = Db.getSqlPara("userOps.queryUoByCdn", Kv.by("cond", kv));
        Page page = UserOps.dao.paginate(getPN(), getPS(), sqlPara);
        renderJson(JSON.toJSONString(page, SerializerFeature.DisableCircularReferenceDetect));
    }

    public void view() {
        int uoId = getParaToInt("id");
        UserOps userOps = UserOps.dao.findFirstByCache(Consts.CACHE_NAMES.userOps.name(), "view_" + uoId, "select * from mt_user_ops where id=?", uoId);
        renderJson(JSON.toJSONString(userOps, SerializerFeature.DisableCircularReferenceDetect));
    }
}
