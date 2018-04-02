package com.yhh.csap.www.at;

import com.jfinal.plugin.activerecord.Db;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.www.at]
 * 类名称:    [AtMeSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/2]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class AtMeSrv {

    public void delAtMeByTarget(String targetObj,int targetId){
        Db.delete("delete from www_at_me where targetObj=? and targetId=?",targetObj,targetId);
    }
}
