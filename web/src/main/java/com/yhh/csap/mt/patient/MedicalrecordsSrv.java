package com.yhh.csap.mt.patient;

import com.jfinal.plugin.activerecord.Db;
import com.yhh.csap.mt.UserMedicalrecords;
import com.yhh.csap.mt.UserMedicalrecordsDoctor;
import com.yhh.csap.mt.UserMedicalrecordsTax;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.patient]
 * 类名称:    [MedicalrecordsSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/31]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class MedicalrecordsSrv {

    public void addMedicalrecordsTax(String[] taxIds,Integer umId, UserMedicalrecords.TaxType taxType){
        Integer id=null;
        UserMedicalrecordsTax userMedicalrecordsTax=null;
        for (String s:taxIds){
            id=Integer.parseInt(s);
            userMedicalrecordsTax=new UserMedicalrecordsTax();
            userMedicalrecordsTax.setUmId(umId);
            userMedicalrecordsTax.setTaxId(id);
            userMedicalrecordsTax.setType(taxType.getVal());
            userMedicalrecordsTax.save();
        }
    }

    public void addMedicalrecordsDoctor(String[] drIds,Integer umId){
        Integer id=null;
        UserMedicalrecordsDoctor userMedicalrecordsDoctor=null;
        for (String s:drIds){
            id=Integer.parseInt(s);
            userMedicalrecordsDoctor=new UserMedicalrecordsDoctor();
            userMedicalrecordsDoctor.setUmId(umId);
            userMedicalrecordsDoctor.setDId(id);
            userMedicalrecordsDoctor.save();
        }
    }


    public void delMedicalrecordsTaxByUmId(Integer umId){
        Db.delete("delete from mt_user_medicalrecords_tax where umId=?",umId);
    }

    public void delMeicalrecordsDoctorByUmId(Integer umId){
        Db.delete("delete from mt_user_medicalrecords_doctor where umId=?",umId);
    }
}
