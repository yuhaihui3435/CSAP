package com.yhh.csap.www.like;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.www.model.LikeRecords;

import java.util.Date;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.www.like]
 * 类名称:    [LikeRecordsSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/23]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class LikeRecordsSrv {
    @Before(Tx.class)
    public void LikeAddSave(LikeRecords likeRecords){
        likeRecords.setCAt(new Date());
        likeRecords.save();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(likeRecords.getTargetObj()).append(" set laudCount=laudCount+1 where id=?");
        Db.update(stringBuilder.toString(),likeRecords.getTargetId());
    }
    @Before(Tx.class)
    public void LikeSubSave(LikeRecords likeRecords){
        likeRecords.delete();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(likeRecords.getTargetObj()).append(" set laudCount=laudCount-1 where id=?");
        Db.update(stringBuilder.toString(),likeRecords.getTargetId());
    }

}
