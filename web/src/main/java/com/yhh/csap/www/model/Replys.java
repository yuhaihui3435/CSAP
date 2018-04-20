package com.yhh.csap.www.model;


import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.kits.DateKit;
import com.yhh.csap.www.model.base.BaseReplys;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Replys extends BaseReplys<Replys> {
	public static final Replys dao = new Replys().dao();

	@Override
	public String getTableName() {
		return "www_replys";
	}

	public User getReplyer(){
		return getUserId()!=null?User.dao.findFirstByCache(Consts.CACHE_NAMES.user.name(),"id_"+getUserId(),"select * from s_user where id=?",getUserId()):null;
	}
	public String getCAtTxt(){
		return getCAt()!=null?DateKit.dateToStr(getCAt(),DateKit.STR_DATEFORMATE):"";
	}
}
