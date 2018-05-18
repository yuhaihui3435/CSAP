package com.yhh.csap.www.uc;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Page;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.core.CoreException;
import com.yhh.csap.kits.ResKit;
import com.yhh.csap.www.model.PostInfo;
import com.yhh.csap.www.model.Replys;
import com.yhh.csap.www.postInfo.PostInfoSrv;
import com.yhh.csap.www.replys.ReplysSrv;
import javafx.geometry.Pos;

import java.sql.Struct;
import java.util.List;

/**
 *
 * 用户中心
 *
 */
public class UcCtr extends CoreController {


    private PostInfoSrv postInfoSrv=enhance(PostInfoSrv.class.getSimpleName(),PostInfoSrv.class);
    private ReplysSrv replysSrv=enhance(ReplysSrv.class.getSimpleName(),ReplysSrv.class);

    public void index(){
        Integer uId=null;
        String id_str=getPara(0);
        if(StrUtil.isBlank(id_str)){
            if(currUser()==null){
                throw new CoreException(ResKit.getMsg(Consts.ILLEGAL_OPERATION_MSG_KEY));
            }
            uId=currUser().getId().intValue();
        }else{
            uId=Integer.parseInt(id_str);
            setAttr("qId",uId);//查询用户中心的来源是其他人查看。

        }

        setAttr("sm","bs");
        render("main.html");
    }



    public void dyn(){
        Integer uId=null;
        String id_str=getPara("qId");
        if(StrUtil.isBlank(id_str)){
            if(currUser()==null){
                throw new CoreException(ResKit.getMsg(Consts.ILLEGAL_OPERATION_MSG_KEY));
            }
            uId=currUser().getId().intValue();
        }else{
            uId=Integer.parseInt(id_str);
            setAttr("qId",uId);//查询用户中心的来源是其他人查看。
        }
        String qt=getPara("qt");
        if(StrUtil.isBlank(qt)||qt.equals("pi")){
            Page<PostInfo> postInfoPage= postInfoSrv.findByOperId(getPN(),getPS(),uId);
            setAttr("qt","pi");
            setAttr("postInfoPage",postInfoPage);
        }else if(StrUtil.isNotBlank(qt)&&qt.equals("r")){
            Page<Replys> replysPage=replysSrv.findReplysByUserIdForPost(getPN(),getPS(),uId);
            setAttr("qt","r");
            setAttr("replysPage",replysPage);
        }
        else if(StrUtil.isNotBlank(qt)&&qt.equals("tmr")){
            Page<Replys> replysPage=replysSrv.findReplysByUserIdForPost(getPN(),getPS(),uId);
            setAttr("qt","r");
            setAttr("replysPage",replysPage);
        }
        setAttr("postInfoCount",postInfoSrv.countByOperId(uId));
        setAttr("replysCount",replysSrv.countByUserIdForPost(uId));
        setAttr("commentCount",replysSrv.countByUserIdForOther(uId));
        setAttr("sm","dyn");
        render("dyn.html");
    }


    public void msg(){
        Integer uId=currUser().getId().intValue();
        setAttr("sm","msg");
        render("msg.html");
    }


    public void collect(){



        setAttr("sm","collect");
        render("collect.html");
    }



}
