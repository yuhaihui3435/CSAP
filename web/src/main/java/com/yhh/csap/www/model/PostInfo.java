package com.yhh.csap.www.model;


import com.yhh.csap.www.model.base.BasePostInfo;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class PostInfo extends BasePostInfo<PostInfo> {
	public static final PostInfo dao = new PostInfo().dao();

	@Override
	public String getTableName() {
		return "www_post_info";
	}
}