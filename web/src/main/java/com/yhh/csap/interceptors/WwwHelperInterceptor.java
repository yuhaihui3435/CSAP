package com.yhh.csap.interceptors;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.www.model.Collect;
import com.yhh.csap.www.model.LikeRecords;

/**
 * 简介
 * <p>      判断当前用户是否已经收藏，点赞过当前的访问的内容
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.interceptors]
 * 类名称:    [WwwHelperInterceptor]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/23]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class WwwHelperInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        User user=(User)invocation.getController().getAttr(Consts.CURR_USER);
        if(user!=null){
            String ak=invocation.getActionKey();
            ak=ak.replaceAll("/","").replaceAll("View","");
            String tableName=Consts.MAPPING_TO_TBL.get(ak);
            if(StrUtil.isNotBlank(tableName)) {
                //判断是否点赞过
                LikeRecords likeRecords=LikeRecords.dao.findFirstByCache(Consts.CACHE_NAMES.hold.name(),"likeRecords_".concat(user.getId().toString()).concat(StrUtil.UNDERLINE).concat(tableName).concat(StrUtil.UNDERLINE).concat(invocation.getController().getPara(0)),"select * from www_like_records where userId=? and targetObj=? and targetId=?",user.getId(),tableName,invocation.getController().getParaToInt(0));
                if(likeRecords!=null){
                    invocation.getController().setAttr(Consts.THUMB_UP_TOKEN,Consts.YORN_STR.yes.name());
                }else{
                    invocation.getController().setAttr(Consts.THUMB_UP_TOKEN,Consts.YORN_STR.no.name());
                }
                //判断是否收藏过
                Collect collect=Collect.dao.findFirstByCache(Consts.CACHE_NAMES.hold.name(),"collect_".concat(user.getId().toString()).concat(StrUtil.UNDERLINE).concat(tableName).concat(StrUtil.UNDERLINE).concat(invocation.getController().getPara(0)),"select * from www_collect where userId=? and targetObj=? and targetId=?",user.getId(),tableName,invocation.getController().getParaToInt(0));
                if(collect!=null){
                    invocation.getController().setAttr(Consts.COLLECT_TOKEN,Consts.YORN_STR.yes.name());
                }else{
                    invocation.getController().setAttr(Consts.COLLECT_TOKEN,Consts.YORN_STR.no.name());
                }
            }
        }
        invocation.invoke();
    }
}
