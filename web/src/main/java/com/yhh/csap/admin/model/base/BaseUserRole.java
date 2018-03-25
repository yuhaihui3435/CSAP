package com.yhh.csap.admin.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserRole<M extends BaseUserRole<M>> extends CoreModel<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setUId(java.lang.Long uId) {
		set("uId", uId);
	}

	public java.lang.Long getUId() {
		return getLong("uId");
	}

	public void setRId(java.lang.Integer rId) {
		set("rId", rId);
	}

	public java.lang.Integer getRId() {
		return getInt("rId");
	}

}
