package com.yhh.csap.www.like;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.Consts;
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
        if(findByTId_TObj_UId(likeRecords)!=null)return;
        likeRecords.setTargetObj(Consts.MAPPING_TO_TBL.get(likeRecords.getTargetObj()));
        likeRecords.setCAt(new Date());
        likeRecords.save();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(likeRecords.getTargetObj()).append(" set laudCount=laudCount+1 where id=?");
        Db.update(stringBuilder.toString(),likeRecords.getTargetId());
    }
    @Before(Tx.class)
    public void LikeSubSave(LikeRecords likeRecords){
        likeRecords.setTargetObj(Consts.MAPPING_TO_TBL.get(likeRecords.getTargetObj()));
        Db.delete("delete from www_like_records where targetId=? and targetObj=? and userId=? ",likeRecords.getTargetId(),likeRecords.getTargetObj(),likeRecords.getUserId());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(likeRecords.getTargetObj()).append(" set laudCount=laudCount-1 where id=?");
        Db.update(stringBuilder.toString(),likeRecords.getTargetId());
    }

    public LikeRecords findByTId_TObj_UId(LikeRecords obj){
       return LikeRecords.dao.findFirst("select * from www_like_records where targetId=? and targetObj=? and userId=?",obj.getTargetId(),obj.getTargetObj(),obj.getUserId());
    }

}
