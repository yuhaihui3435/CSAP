package com.yhh.csap.www.uc;

import cn.hutool.core.util.StrUtil;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.core.CoreException;
import com.yhh.csap.kits.ResKit;

/**
 *
 * 用户中心
 *
 */
public class UcCtr extends CoreController {


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
            setAttr("qs","fromOther");//查询用户中心的来源是其他人查看。
        }

        setAttr("sm","bs");
        render("main.html");
    }



    public void dyn(){
        Integer uId=null;
        String id_str=getPara(0);
        if(StrUtil.isBlank(id_str)){
            if(currUser()==null){
                throw new CoreException(ResKit.getMsg(Consts.ILLEGAL_OPERATION_MSG_KEY));
            }
            uId=currUser().getId().intValue();
        }else{
            uId=Integer.parseInt(id_str);
            setAttr("qs","fromOther");//查询用户中心的来源是其他人查看。
        }

        setAttr("sm","dyn");
        render("main.html");
    }


    public void msg(){
        Integer uId=currUser().getId().intValue();
        setAttr("sm","msg");
        render("main.html");
    }

}
