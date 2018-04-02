package com.yhh.csap.www.post;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.kits.WordFilter;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.PostInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.hutool.core.util.StrUtil.isNotBlank;
import static cn.hutool.core.util.StrUtil.lowerFirst;

public class PostCtr extends CoreController {

    private final PostSrv postSrv=enhance(PostSrv.class.getSimpleName(),PostSrv.class);

    public void List(){
        int taxId=getParaToInt("taxId");
        String search=getPara("search");
        String ifTop=getPara("ifTop");
        Kv kv=Kv.create();
        if(isNotBlank(ifTop)){
            kv.put("p.ifTop=",ifTop);
        }
        if(isNotBlank(search)){
            Map map=new LinkedHashMap();
            map.put(" (p.title like", StrUtil.join("%",search,"%"));
            map.put(" or u.nickname like",StrUtil.join("%",search,"%",")"));
            kv.set(map);
        }
        if(taxId!=0){
            kv.put("p.taxId=",taxId);
        }
        SqlPara sqlPara= Db.getSqlPara("post.findPage",Kv.by("cond",kv));
        Page page= PostInfo.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(page);
    }
    @Before({Tx.class})
    public void save(){

        PostInfo postInfo=getModel(PostInfo.class,"",true);
        long userId=currUser().getId().longValue();
        postInfo.setOperId(userId);
        postInfo.setStatus(Consts.STATUS.enable.getVal());
        postInfo.setCheckStatus(Consts.CHECK_STATUS.waitingCheck.getVal());
        postInfo.setCAt(new Date());
        postInfo.save();
        String txt = postInfo.getContent();
        if (isNotBlank(txt)) {
            txt = WordFilter.doFilter(txt);
            LogKit.info("文本敏感词检查结果为:"+txt);
        }

        Pattern pattern = Pattern.compile("@[^\\s]+\\s?");
        Matcher m = pattern.matcher(txt);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            if (!strs.contains(m.group()))
                strs.add(m.group());
        }


        User user = null;
        AtMe at = null;
        String _s = null;
        String _txt = txt;
        String author = null;
        for (String s : strs) {
            _s = s;
            s = s.replace("@", "");
            s = s.trim();
            user = User.dao.findByNickname(s);
            if(user==null)continue;
            _txt = _txt.replaceAll(_s, "<a href=\"" + getRequest().getContextPath() + "/userInfo/" + user.getId().intValue() + "\" target=\"_blank\">" + _s + "</a>");
            if (user != null && user.getId().intValue() != userId) {
                at = new AtMe();
                at.setCAt(new Date());
                at.setTargetId(postInfo.getId());
                at.setTargetObj("postInfo");
                at.setUserId(user.getId().intValue());
                at.save();
            } else {
                author = _s;
            }
        }

        renderSuccessJSON("发布成功");
    }

    @Before({Tx.class})
    public void update(){

        PostInfo postInfo=getModel(PostInfo.class,"",true);
        long userId=currUser().getId().longValue();
        postInfo.setMAt(new Date());
        postInfo.update();
        String txt = postInfo.getContent();
        if (isNotBlank(txt)) {
            txt = WordFilter.doFilter(txt);
            LogKit.info("文本敏感词检查结果为:"+txt);
        }

        postSrv.delAtMeByPostId(postInfo.getId());//删除之前的atme数据

        Pattern pattern = Pattern.compile("@[^\\s]+\\s?");
        Matcher m = pattern.matcher(txt);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            if (!strs.contains(m.group()))
                strs.add(m.group());
        }

        User user = null;
        AtMe at = null;
        String _s = null;
        String _txt = txt;
        String author = null;
        for (String s : strs) {
            _s = s;
            s = s.replace("@", "");
            s = s.trim();
            user = User.dao.findByNickname(s);
            if(user==null)continue;
            _txt = _txt.replaceAll(_s, "<a href=\"" + getRequest().getContextPath() + "/userInfo/" + user.getId().intValue() + "\" target=\"_blank\">" + _s + "</a>");
            if (user != null && user.getId().intValue() != userId) {
                at = new AtMe();
                at.setCAt(new Date());
                at.setTargetId(postInfo.getId());
                at.setTargetObj("postInfo");
                at.setUserId(user.getId().intValue());
                at.save();
            } else {
                author = _s;
            }
        }

        renderSuccessJSON("发布更新成功");
    }





}
