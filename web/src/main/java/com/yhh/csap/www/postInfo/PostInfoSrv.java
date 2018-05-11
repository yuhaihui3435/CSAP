package com.yhh.csap.www.postInfo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.yhh.csap.Consts;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.Collect;
import com.yhh.csap.www.model.PostInfo;

import java.util.List;

public class PostInfoSrv {


    public boolean ifCollect(String tObj,int tId,int userId){
        List list=Collect.dao.findByCache(Consts.CACHE_NAMES.postInfo.name(),"ifCollect_".concat(tObj).concat("_").concat(tId+""),"select * from www_collect where targetObj=? and targetId=? and userId=?",tObj,tId,userId);
        return list.isEmpty()?false:true;
    }

    public Page<PostInfo> findByOperId(int pn, int ps, Integer id){
        String sql=" from www_post_info where operId=? and dAt is null and checkStatus='00' and status='0'";
        return PostInfo.dao.paginate(pn,ps,"select * ", sql,id);
    }


    public Long countByOperId(Integer id){
        String sql="select count(*) from www_post_info where operId=? and dAt is null and checkStatus='00' and status='0' order by cAt desc";
        return Db.queryLong(sql,id);
    }
}
