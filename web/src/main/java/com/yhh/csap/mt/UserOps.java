package com.yhh.csap.mt;


import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.mt.base.BaseUserOps;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class UserOps extends BaseUserOps<UserOps> {
	public static final UserOps dao = new UserOps().dao();

	@Override
	public String getTableName() {
		return "mt_user_ops";
	}

	public User getUser(){
		return User.dao.findUserByIdWithCache(getUserId());
	}
	public String getTreatEffectTxt(){
		return getTmEftId()!=null?((Taxonomy)CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),getTmEftId().toString())).getTitle():Consts.BLANK;
	}
//	public List<UserMedicalrecordsTax> getTreatEffectTax(){
//		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"treatEffectTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='1'",getId());
//	}

	public List<UserMedicalrecordsTax> getOpModeTax(){
		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"opModelTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='2'",getId());
	}

	public List<UserMedicalrecordsTax> getBOpSymptomsTax(){
		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"bOpSymptomsTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='3'",getId());
	}

	public List<UserMedicalrecordsTax> getAfOpSymptomsTax(){
		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"afOpSymptomsTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='4'",getId());
	}
//	public List<UserMedicalrecordsTax> getDiseasesTax(){
//		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"diseasesTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='5'",getId());
//	}

//	public List<UserMedicalrecordsTax> getSymptomsTax(){
//		return UserMedicalrecordsTax.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"symptomsTax_"+getId(),"select * from mt_user_medicalrecords_tax where umId=? and type='6'",getId());
//	}


	public List<UserMedicalrecordsDoctor> getDrs(){
		return UserMedicalrecordsDoctor.dao.findByCache(Consts.CACHE_NAMES.userMedicalrecords.name(),"doctors_"+getId(),"select * from mt_user_medicalrecords_doctor where umId=?",getId() );
	}
	public String getHospTxt(){
		return getHosp()==null?Consts.CHAR_WU:((Taxonomy) CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),getHosp().toString())).getTitle();
	}



}
