package com.yhh.csap.www.postInfo;

import com.jfinal.plugin.activerecord.Db;
import com.yhh.csap.Consts;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.Collect;

import java.util.List;

public class PostInfoSrv {


    public boolean ifCollect(String tObj,int tId,int userId){
        List list=Collect.dao.findByCache(Consts.CACHE_NAMES.postInfo.name(),"ifCollect_".concat(tObj).concat("_").concat(tId+""),"select * from www_collect where targetObj=? and targetId=? and userId=?",tObj,tId,userId);
        return list.isEmpty()?false:true;
    }

}
