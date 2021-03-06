package com.yhh.csap.www.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BasePriLetter<M extends BasePriLetter<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public void setFromUserId(Integer fromUserId) {
		set("fromUserId", fromUserId);
	}
	
	public Integer getFromUserId() {
		return getInt("fromUserId");
	}

	public void setToUserId(Integer toUserId) {
		set("toUserId", toUserId);
	}
	
	public Integer getToUserId() {
		return getInt("toUserId");
	}

	public void setContent(String content) {
		set("content", content);
	}
	
	public String getContent() {
		return getStr("content");
	}

	public void setCAt(java.util.Date cAt) {
		set("cAt", cAt);
	}
	
	public java.util.Date getCAt() {
		return get("cAt");
	}

	public void setHasRead(String hasRead) {
		set("hasRead", hasRead);
	}
	
	public String getHasRead() {
		return getStr("hasRead");
	}

}
