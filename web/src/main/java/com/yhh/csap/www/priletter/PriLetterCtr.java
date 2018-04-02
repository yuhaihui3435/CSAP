package com.yhh.csap.www.priletter;

import com.jfinal.plugin.activerecord.Page;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.PriLetter;

import java.util.Date;

public class PriLetterCtr extends CoreController {

    public void list(){
        int userId=currUser().getId().intValue();
        String hasRead=getPara("hasRead");
        String sql="from where fromUserId=? or toUserId=? and hasRead=?";
        Page page= PriLetter.dao.paginate(getPN(),getPS(),"select * ",sql,userId,userId,hasRead);
        renderJson(page);
    }

    public void save(){
        PriLetter priLetter=getModel(PriLetter.class,"",true);
        priLetter.setCAt(new Date());
        priLetter.setHasRead(Consts.YORN_STR.no.getVal());
        priLetter.save();
        renderSuccessJSON("私信发送成功");
    }

    public void del(){
        int id=getParaToInt("id");
        PriLetter.dao.deleteById(id);
        renderSuccessJSON("私信删除成功");
    }

    public void setRead(){
        int id=getParaToInt("id");
        PriLetter priLetter=PriLetter.dao.findById(id);
        priLetter.setHasRead(Consts.YORN_STR.yes.getVal());
        priLetter.update();
        renderSuccessJSON("私信状态设置成功");
    }


}
