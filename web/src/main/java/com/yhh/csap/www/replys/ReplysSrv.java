package com.yhh.csap.www.replys;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.www.model.Replys;

import java.util.List;

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
        stringBuilder.append("update ").append(replys.getTargetObj()).append(" set commentCount=commentCount+1,commentTime=now() where id=?");
        Db.update(stringBuilder.toString(),replys.getTargetId());
    }

    @Before(Tx.class)
    public void replysSubSave(Replys replys){
        replys.update();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ").append(replys.getTargetObj()).append(" set commentCount=commentCount-1 where id=?");
        Db.update(stringBuilder.toString(),replys.getTargetId());
    }

    public Page<Replys> findReplysByUserIdForReplyed(int pn,int ps,Integer userId){
        String sql=" from   (select * from www_reply where dAt is null and userId=? and wr.targetObj='www_post_info' order by wr.cAt desc) t left join www_replys wr on t.id=wr.targetId ";
        return Replys.dao.paginate(pn,ps,"select t.* ",sql,userId);
    }
    public Page<Replys> findReplysByUserIdForPost(int pn,int ps,Integer userId){
        String sql=" from www_replys where dAt is null and userId=? and targetObj='www_post_info' order by cAt desc";
        return Replys.dao.paginate(pn,ps,"select * ",sql,userId);
    }

    public Page<Replys> findReplysByUserIdForOther(int pn,int ps,Integer userId){
        String sql=" from www_replys where dAt is null and userId=? and targetObj!='www_post_info' order by cAt desc";
        return Replys.dao.paginate(pn,ps,"select * ",sql,userId);
    }

    public long countByUserIdForPost(Integer userId){
        String sql="select count(*) from www_replys where dAt is null and userId=? and targetObj='www_post_info'";
        return Db.queryLong(sql,userId);
    }

    public long countByUserIdForOther(Integer userId){
        String sql="select count(*) from www_replys where dAt is null and userId=? and targetObj!='www_post_info'";
        return Db.queryLong(sql,userId);
    }
}
