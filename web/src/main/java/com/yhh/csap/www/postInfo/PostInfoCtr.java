package com.yhh.csap.www.postInfo;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.kits.WordFilter;
import com.yhh.csap.www.at.AtMeSrv;
import com.yhh.csap.www.model.AtMe;
import com.yhh.csap.www.model.LikeRecords;
import com.yhh.csap.www.model.PostInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.hutool.core.util.StrUtil.isNotBlank;

public class PostInfoCtr extends CoreController {

    private final PostInfoSrv postSrv=enhance(PostInfoSrv.class.getSimpleName(),PostInfoSrv.class);
    private final AtMeSrv atMeSrv=enhance(AtMeSrv.class.getSimpleName(),AtMeSrv.class);

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
        SqlPara sqlPara= Db.getSqlPara("postInfo.findPage",Kv.by("cond",kv));
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

        postInfo.setContent(_txt);
        postInfo.update();
        CacheKit.remove(Consts.CACHE_NAMES.postInfo.name(),"id_"+postInfo.getId());
        renderSuccessJSON("发布成功");
    }

    @Before({Tx.class})
    public void update(){

        PostInfo postInfo=getModel(PostInfo.class,"",true);
        long userId=currUser().getId().longValue();
        postInfo.setMAt(new Date());

        String txt = postInfo.getContent();
        if (isNotBlank(txt)) {
            txt = WordFilter.doFilter(txt);
            LogKit.info("文本敏感词检查结果为:"+txt);
        }

        atMeSrv.delAtMeByTarget("",postInfo.getId());//删除之前的atme数据

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
        postInfo.setContent(_txt);
        postInfo.update();
        CacheKit.remove(Consts.CACHE_NAMES.postInfo.name(),"id_"+postInfo.getId());
        renderSuccessJSON("发布更新成功");
    }


    public void del(){
        int id=getParaToInt("id");
        PostInfo postInfo=PostInfo.dao.findById(id);
        postInfo.setDAt(new Date());
        postInfo.update();
        CacheKit.remove(Consts.CACHE_NAMES.postInfo.name(),"id_"+postInfo.getId());
        renderSuccessJSON("发布内容删除成功");
    }

    public void clean(){
        int id=getParaToInt("id");
        PostInfo.dao.deleteById(id);
        CacheKit.remove(Consts.CACHE_NAMES.postInfo.name(),"id_"+id);
        renderSuccessJSON("发布内容清除成功");
    }

    public void index(){
        final int id=getParaToInt(0);
        //访问量+1
        Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                Db.update("update www_post_info set viewCount=viewCount+1 where id=?",id);
                return true;
            }
        });
        renderJson(PostInfo.dao.findByCache(Consts.CACHE_NAMES.postInfo.name(),"id_"+id,"select * from www_post_info where id=? and dAt is null",id));
    }
    @Before(Tx.class)
    public void laud(){
        LikeRecords likeRecords=new LikeRecords();
        int piId=getParaToInt(0);
        Db.update("update www_post_info set likeCount=likeCount+1 where id=?",piId);
        likeRecords.setUserId(currUser().getId().intValue());
        likeRecords.setCAt(new Date());
        likeRecords.setTargetId(piId);
        likeRecords.setTargetObj("postInfo");
        likeRecords.save();
        renderSuccessJSON("点赞成功");
    }
    @Before(Tx.class)
    public void unLand(){
        int piId=getParaToInt(0);
        Db.update("update www_post_info set likeCount=likeCoun-1 where id=?",piId);
        LikeRecords likeRecords=LikeRecords.dao.findFirst("select * from www_like_records where targetObj=? and targetId=?","postInfo",piId);
        likeRecords.delete();
        renderSuccessJSON("点赞取消成功");
    }


}
