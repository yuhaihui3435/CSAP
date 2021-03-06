package com.yhh.csap.admin.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends CoreModel<M> implements IBean {

	public void setId(java.math.BigInteger id) {
		set("id", id);
	}

	public java.math.BigInteger getId() {
		return get("id");
	}

	public void setIdcard(java.lang.String idcard) {
		set("idcard", idcard);
	}

	public java.lang.String getIdcard() {
		return getStr("idcard");
	}

	public void setNickname(java.lang.String nickname) {
		set("nickname", nickname);
	}

	public java.lang.String getNickname() {
		return getStr("nickname");
	}

	public void setScore(java.lang.Integer score) {
		set("score", score);
	}

	public java.lang.Integer getScore() {
		return getInt("score");
	}

	public void setAvatar(java.lang.String avatar) {
		set("avatar", avatar);
	}

	public java.lang.String getAvatar() {
		return getStr("avatar");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}

	public java.lang.String getEmail() {
		return getStr("email");
	}

	public void setSignature(java.lang.String signature) {
		set("signature", signature);
	}

	public java.lang.String getSignature() {
		return getStr("signature");
	}

	public void setThirdId(java.lang.String thirdId) {
		set("third_id", thirdId);
	}

	public java.lang.String getThirdId() {
		return getStr("third_id");
	}

	public void setAccessToken(java.lang.String accessToken) {
		set("access_token", accessToken);
	}

	public java.lang.String getAccessToken() {
		return getStr("access_token");
	}

	public void setReceiveMsg(java.lang.Boolean receiveMsg) {
		set("receive_msg", receiveMsg);
	}

	public java.lang.Boolean getReceiveMsg() {
		return get("receive_msg");
	}

	public void setCAt(java.util.Date cAt) {
		set("c_at", cAt);
	}

	public java.util.Date getCAt() {
		return get("c_at");
	}

	public void setMAt(java.util.Date mAt) {
		set("m_at", mAt);
	}

	public java.util.Date getMAt() {
		return get("m_at");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}

	public java.lang.String getPhone() {
		return getStr("phone");
	}

	public void setChannel(java.lang.String channel) {
		set("channel", channel);
	}

	public java.lang.String getChannel() {
		return getStr("channel");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return getStr("status");
	}

	public void setThirdAccessToken(java.lang.String thirdAccessToken) {
		set("third_access_token", thirdAccessToken);
	}

	public java.lang.String getThirdAccessToken() {
		return getStr("third_access_token");
	}

	public void setLogged(java.util.Date logged) {
		set("logged", logged);
	}

	public java.util.Date getLogged() {
		return get("logged");
	}

	public void setActivated(java.util.Date activated) {
		set("activated", activated);
	}

	public java.util.Date getActivated() {
		return get("activated");
	}

	public void setEmailStatus(java.lang.Boolean emailStatus) {
		set("email_status", emailStatus);
	}

	public java.lang.Boolean getEmailStatus() {
		return get("email_status");
	}

	public void setPhoneStatus(java.lang.Boolean phoneStatus) {
		set("phone_status", phoneStatus);
	}

	public java.lang.Boolean getPhoneStatus() {
		return get("phone_status");
	}

	public void setContentCount(java.lang.Integer contentCount) {
		set("content_count", contentCount);
	}

	public java.lang.Integer getContentCount() {
		return getInt("content_count");
	}

	public void setCommentCount(java.lang.Integer commentCount) {
		set("comment_count", commentCount);
	}

	public java.lang.Integer getCommentCount() {
		return getInt("comment_count");
	}

	public void setIdcardtype(java.lang.Integer idcardtype) {
		set("idcardtype", idcardtype);
	}

	public java.lang.Integer getIdcardtype() {
		return getInt("idcardtype");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}

	public void setSalt(java.lang.String salt) {
		set("salt", salt);
	}

	public java.lang.String getSalt() {
		return getStr("salt");
	}

	public void setLoginname(java.lang.String loginname) {
		set("loginname", loginname);
	}

	public java.lang.String getLoginname() {
		return getStr("loginname");
	}

	public void setIsAdmin(java.lang.String isAdmin) {
		set("isAdmin", isAdmin);
	}

	public java.lang.String getIsAdmin() {
		return getStr("isAdmin");
	}

	public void setUnionid(java.lang.String unionid) {
		set("unionid", unionid);
	}

	public java.lang.String getUnionid() {
		return getStr("unionid");
	}

	public void setDAt(java.util.Date dAt) {
		set("d_at", dAt);
	}

	public java.util.Date getDAt() {
		return get("d_at");
	}

	public java.lang.String getSheng() {
		return getStr("sheng");
	}

	public void setQu(java.lang.String qu) {
		set("qu", qu);
	}

	public java.lang.String getQu() {
		return getStr("qu");
	}

	public void setShi(java.lang.String shi) {
		set("shi", shi);
	}

	public java.lang.String getShi() {
		return getStr("shi");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return getStr("sex");
	}

	public void setAge(java.lang.String age) {
		set("age", age);
	}

	public java.lang.String getAge() {
		return getStr("age");
	}

}
