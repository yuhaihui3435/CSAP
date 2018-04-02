package com.yhh.csap.www.replys;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.www.model.Replys;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.www.replys]
 * 类名称:    [ReplysSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/2]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class ReplysSrv {
    @Before(Tx.class)
    public void replysAddSave(Replys replys){
        replys.save();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(replys.getTargetObj()).append(" set replyCount=replyCount+1,lastReply=now() where id=?");
        Db.update(stringBuilder.toString());
    }

    @Before(Tx.class)
    public void replysSubSave(Replys replys){
        replys.save();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(replys.getTargetObj()).append(" set replyCount=replyCount-1 where id=?");
        Db.update(stringBuilder.toString());
    }
}
