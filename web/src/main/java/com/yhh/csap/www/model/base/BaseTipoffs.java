package com.yhh.csap.www.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

import static com.jfinal.kit.PropKit.getInt;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTipoffs<M extends BaseTipoffs<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public void setTargetObj(String targetObj) {
		set("targetObj", targetObj);
	}
	
	public String getTargetObj() {
		return getStr("targetObj");
	}

	public void setTargetId(String targetId) {
		set("targetId", targetId);
	}
	
	public String getTargetId() {
		return getStr("targetId");
	}

	public void setCAt(java.util.Date cAt) {
		set("cAt", cAt);
	}
	
	public java.util.Date getCAt() {
		return get("cAt");
	}

	public void setUserId(Integer userId) {
		set("userId", userId);
	}
	
	public Integer getUserId() {
		return getInt("userId");
	}

	public void setProcesserId(Integer processerId) {
		set("processerId", processerId);
	}
	
	public Integer getProcesserId() {
		return getInt("processerId");
	}

	public void setTax(Integer tax) {
		set("tax", tax);
	}
	
	public Integer getTax() {
		return getInt("tax");
	}

	public void setProcessAt(java.util.Date processAt) {
		set("processAt", processAt);
	}
	
	public java.util.Date getProcessAt() {
		return get("processAt");
	}

	public void setRemark(String remark) {
		set("remark", remark);
	}
	
	public String getRemark() {
		return getStr("remark");
	}

	public void setProcessRet(String processRet) {
		set("processRet", processRet);
	}
	
	public String getProcessRet() {
		return getStr("processRet");
	}

}
