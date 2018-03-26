package com.yhh.csap.admin.user;

import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;

import java.util.Date;

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

    public void addDR(User user){
        user.setCAt(new Date());
        user.setStatus(Consts.STATUS.enable.getVal());
        user.setIsAdmin(Consts.YORN_STR.no.getVal());

    }
}
