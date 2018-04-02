package com.yhh.csap.www.post;

import com.yhh.csap.admin.model.User;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.PostInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostSrv {


    public void delAtMeByPostId(int id){
        List<AtMe> list=AtMe.dao.find("select * from www_at_me where targetObj=? and targetIdentical=?","postInfo",id);
    }


}
