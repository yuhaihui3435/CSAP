package com.yhh.csap.www.replys;

import cn.hutool.core.util.NumberUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.sun.org.apache.regexp.internal.RE;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.kits.WordFilter;
import com.yhh.csap.www.at.AtMeSrv;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.Replys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.hutool.core.util.StrUtil.isNotBlank;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.www.replys]
 * 类名称:    [ReplysCtr]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/2]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class ReplysCtr extends CoreController {



    private final ReplysSrv replysSrv=enhance(ReplysSrv.class.getSimpleName(),ReplysSrv.class);
    private final AtMeSrv atMeSrv=enhance(AtMeSrv.class.getSimpleName(),AtMeSrv.class);
    @Before(CacheInterceptor.class)
    @CacheName("replys")
    public void list(){
        int targetId=getParaToInt("targetId");
        String targetObj=getPara("tragetObj");
        String sql=" from www_replys where tragetId=? and targetObj=? and dAt is null order by bestReply,cAt desc ";
        Page page= Replys.dao.paginate(getPN(),getPS(),"select * ",sql,targetId,targetObj);
        renderJson(page);
    }

    public void index(){

    }

    @Before({EvictInterceptor.class, Tx.class})
    @CacheName("replys")
    public void save(){
        Replys replys=getModel(Replys.class,"",true);
        int userId=currUser().getId().intValue();
        replys.setTargetObj(Consts.MAPPING_TO_TBL.get(replys.getTargetObj()));
        replys.setCAt(new Date());
        replys.setUserId(userId);
        replys.setBestReply(Consts.YORN_STR.no.getVal());
        replys.setScore(Consts.BD_ZERO);
        replys.setLikeCount(0);
        replysSrv.replysAddSave(replys);

        String txt = replys.getContent();
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
                at.setTargetId(replys.getId());
                at.setTargetObj("www_replys");
                at.setUserId(user.getId().intValue());
                at.save();
            } else {
                author = _s;
            }
        }
        replys.setContent(_txt);
        replys.update();
        renderSuccessJSON("回复成功");
    }
    @Before({EvictInterceptor.class,Tx.class})
    @CacheName("replys")
    public void del(){
        int id=getParaToInt(0);
        Replys replys=Replys.dao.findById(id);
        replys.setDAt(new Date());
        atMeSrv.delAtMeByTarget("replys",replys.getId());//删除之前的atme数据
        replysSrv.replysSubSave(replys);
        renderSuccessJSON("回复删除成功");
    }
    @Before(EvictInterceptor.class)
    @CacheName("replys")
    public void setBestReply(){
        int id=getParaToInt("id");
        Replys replys=Replys.dao.findById(id);
        replys.setBestReply(Consts.YORN_STR.yes.getVal());
        replys.update();
        renderSuccessJSON("最佳答案设置成功");
    }

}