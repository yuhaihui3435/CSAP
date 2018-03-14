package com.yhh.csap.admin.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMapping<M extends BaseMapping<M>> extends CoreModel<M> implements IBean {

	public void setId(Long id) {
		set("id", id);
	}

	public Long getId() {
		return getLong("id");
	}

	public void setCid(Long cid) {
		set("cid", cid);
	}

	public Long getCid() {
		return getLong("cid");
	}

	public void setTid(Long tid) {
		set("tid", tid);
	}

	public Long getTid() {
		return getLong("tid");
	}

}