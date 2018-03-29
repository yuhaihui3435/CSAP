package com.yhh.csap.admin.user;

import static com.xiaoleilu.hutool.util.StrUtil.isNotBlank;

import com.graphbuilder.math.func.LgFunction;
import com.jfinal.aop.Before;
import com.yhh.csap.admin.model.Role;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.admin.model.UserRole;
import com.yhh.csap.core.CoreException;
import static com.yhh.csap.Consts.*;
import static com.jfinal.kit.LogKit.error;
import static com.jfinal.kit.LogKit.info;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.admin.user]
 * 类名称:    [UserSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/26]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class UserSrv {

    public void checkLoginnameAndEmailAndTel(String ...strings) {
        User user = null;
        if(isNotBlank(strings[0])) {
            user = User.dao.findFristByPropEQ("loginname", strings[0]);
            if (user != null) {
                throw new CoreException("CE1");
            }
        }
        if(isNotBlank(strings[1])) {
            user = User.dao.findFristByPropEQ("email", strings[1]);
            if (user != null) {
                throw new CoreException("CE2");
            }
        }
        if(isNotBlank(strings[2])) {
            user = User.dao.findFristByPropEQ("phone", strings[2]);
            if (user != null) {
                throw new CoreException("CE3");
            }
        }
    }

    public void addDr(User user){
        user.save();
        UserRole userRole=new UserRole();
        userRole.setUId(user.getId().longValue());
        Role role= Role.dao.findFristByPropEQ("name",ROLE_DOCTOR);
        if(role!=null){
            userRole.setRId(role.getId().intValue());
            userRole.save();
        }else{
            error("没有找到doctor这个系统内置的角色");
        }
    }

    public void addPatient(User user){
        user.save();
        UserRole userRole=new UserRole();
        userRole.setUId(user.getId().longValue());
        Role role= Role.dao.findFristByPropEQ("name",ROLE_PATIENT);
        if(role!=null){
            userRole.setRId(role.getId().intValue());
            userRole.save();
        }else{
            error("没有找到patien这个系统内置的角色");
        }
    }
}
