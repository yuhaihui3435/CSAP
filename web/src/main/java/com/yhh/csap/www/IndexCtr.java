package com.yhh.csap.www;

import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.yhh.csap.admin.model.Content;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.interceptors.AdminIAuthInterceptor;

import java.util.*;

/**
 * Created by yuhaihui8913 on 2017/12/26.
 */
@Clear(AdminIAuthInterceptor.class)
public class IndexCtr extends CoreController {

    public void index() {
        List<Content> contents=Content.dao.find("select sc.* from  s_content sc left join s_mapping sm on sc.id=sm.cid where dAt is null and module='art' and flag='00' and tid=389 order by pAt desc limit 3");
        setAttr("pInfoList",contents);
        render("index.html");
    }
    public void a(){
        Integer id=getParaToInt(0);
        int pn =getParaToInt(1,1);
        Page<Content> contents=Content.dao.paginate(pn,getPS(),"select sc.* ","from s_content sc left join s_mapping sm on sc.id=sm.cid  where dAt is null and module='art' and flag='00' and sm.tid=? order by pAt desc",id);
        setAttr("cList",contents);
        setAttr("cId",id);
        render("list.html");
    }
    public void c(){
        Integer id=getParaToInt(0);
        int pn =getParaToInt(1,1);
        Page<Content> contents=Content.dao.paginate(pn,getPS(),"select sc.* ","from s_content sc left join s_mapping sm on sc.id=sm.cid  where dAt is null and module='art' and flag='00' and sm.tid=? order by pAt desc",id);
        setAttr("cList",contents);
        setAttr("cId",id);
        render("list_without_img.html");
    }

    public void b(){
        String index0=getPara(0);
        Integer id=0;
        //带有a_的第一位索引数据，表示直接显示详细内容
        if(index0.startsWith("a_")){
            String[] strings=index0.split("_");
            id=Integer.parseInt(strings[1]);
        }else {
            id = getParaToInt(0);
            setAttr("closeBtn","y");
        }
        Content content=Content.dao.findById(id);
        setAttr("content",content);

        render("view.html");
    }

    public void d(){
        Integer id=getParaToInt(0);
        int pn =getParaToInt(1,1);
        Content content=Content.dao.findFirst("select sc.* from s_content sc left join s_mapping sm on sc.id=sm.cid  where dAt is null and module='art' and flag='00' and sm.tid=? order by pAt desc limit 1",id);
        setAttr("content",content);
        render("view.html");
    }

}
