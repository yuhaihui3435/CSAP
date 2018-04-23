package com.yhh.csap.mt.patient;

import com.jfinal.plugin.activerecord.Db;
import com.yhh.csap.mt.*;

public class OpsSrv {

    public void addOpsTax(String[] taxIds,Integer umId, UserMedicalrecords.TaxType taxType){
        Integer id=null;
        UserOpsTax userOpsTax=null;
        for (String s:taxIds){
            id=Integer.parseInt(s);
            userOpsTax=new UserOpsTax();
            userOpsTax.setUoId(umId);
            userOpsTax.setTaxId(id);
            userOpsTax.setType(taxType.getVal());
            userOpsTax.save();
        }
    }

    public void addOpsDoctor(String[] drIds,Integer uoId){
        Integer id=null;
        UserOpsDoctor userOpsDoctor=null;
        for (String s:drIds){
            id=Integer.parseInt(s);
            userOpsDoctor=new UserOpsDoctor();
            userOpsDoctor.setUoId(uoId);
            userOpsDoctor.setDId(id);
            userOpsDoctor.save();
        }
    }


    public void delMedicalrecordsTaxByUmId(Integer uoId){
        Db.delete("delete from mt_user_ops_tax where umId=?",uoId);
    }

    public void delMeicalrecordsDoctorByUmId(Integer uoId){
        Db.delete("delete from mt_user_ops_doctor where umId=?",uoId);
    }
    
}
