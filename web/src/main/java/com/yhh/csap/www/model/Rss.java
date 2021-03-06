package com.yhh.csap.www.model;


import com.jfinal.aop.Duang;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.admin.user.UserSrv;
import com.yhh.csap.kits.DateKit;
import com.yhh.csap.www.model.base.BaseRss;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Rss extends BaseRss<Rss> {
	public static final Rss dao = new Rss().dao();
	private User userDao= User.dao;

	public String getOperTxt(){
		User user=userDao.findUserByIdWithCache(getOperId());
		return user==null?"":user.getNickname();
	}

	public String getExpiredAtTxt(){
		return getExpiredAt()==null?"": DateKit.dateToStr(getExpiredAt(),DateKit.yyyy_MM_dd);
	}

	public String getStatusTxt(){
		return Consts.STATUS.enable.getVal().equals(getStatus())?Consts.STATUS.enable.getValTxt():Consts.STATUS.expired.getVal().equals(getStatus())?Consts.STATUS.expired.getValTxt():Consts.STATUS.forbidden.getValTxt();
	}

	@Override
	public String getTableName() {
		return "www_rss";
	}
}
