package com.yhh.csap.admin.model;

import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.base.BaseRoleRes;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class RoleRes extends BaseRoleRes<RoleRes> {
	public static final RoleRes dao = new RoleRes().dao();
	public  void delByRoleId(int roleId){
		List<RoleRes> roleResList=dao.find("select id from s_role_res where roleId=?",roleId);
		for(RoleRes rr:roleResList){
			rr.delete();
			List<UserRole> userRoles=UserRole.dao.find("select * from s_user_role where rid=?",rr.getRoleId());
			for(UserRole userRole:userRoles){
				CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),userRole.getUid());
				CacheKit.remove(Consts.CACHE_NAMES.userReses.name(),userRole.getUid());
			}
		}
	}

	public Res getRes(){
		return Res.dao.findById(getResId());
	}

	@Override
	public String getTableName() {
		return "s_user_role";
	}
}