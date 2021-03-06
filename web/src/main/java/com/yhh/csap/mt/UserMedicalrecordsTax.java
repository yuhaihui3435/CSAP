package com.yhh.csap.mt;


import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.mt.base.BaseUserMedicalrecordsTax;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class UserMedicalrecordsTax extends BaseUserMedicalrecordsTax<UserMedicalrecordsTax> {
	public static final UserMedicalrecordsTax dao = new UserMedicalrecordsTax().dao();

	@Override
	public String getTableName() {
		return "mt_user_medicalrecords_tax";
	}

	public Taxonomy getTax(){
		return CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),getTaxId().toString());
	}


}
