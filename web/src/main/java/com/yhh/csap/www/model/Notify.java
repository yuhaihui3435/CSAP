package com.yhh.csap.www.model;


import com.yhh.csap.www.model.base.BaseNotify;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Notify extends BaseNotify<Notify> {
	public static final Notify dao = new Notify().dao();

	@Override
	public String getTableName() {
		return "www_notify";
	}
}