package com.yhh.csap.www.model;


import com.yhh.csap.www.model.base.BaseCollect;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Collect extends BaseCollect<Collect> {
	public static final Collect dao = new Collect().dao();

	@Override
	public String getTableName() {
		return "www_collect";
	}
}
