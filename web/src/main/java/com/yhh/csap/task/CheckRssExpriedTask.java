package com.yhh.csap.task;

import com.jfinal.aop.Duang;
import com.jfinal.kit.LogKit;
import com.yhh.csap.kits.DateKit;
import com.yhh.csap.www.model.Rss;
import com.yhh.csap.www.rss.RssSrv;

import java.util.Date;
import java.util.List;

/**
 * 简介
 * <p>         广播过期检查处理
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.task]
 * 类名称:    [CheckRssExpriedTask]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/4/5]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class CheckRssExpriedTask implements Runnable {
    @Override
    public void run() {
        Date now=new Date();
        LogKit.info("开始执行广播过期处理检查=>>"+ DateKit.dateToStr(now,DateKit.yyyy_MM_dd));
        RssSrv rssSrv= Duang.duang(RssSrv.class.getSimpleName(),RssSrv.class);
        List<Rss> rssList=rssSrv.findByExpiredAt(now);
        LogKit.info("检查到有"+rssList.size()+"条数据与今天过期");
        rssSrv.updateRssExpired(rssList);
        LogKit.info("过期数据处理结束");
    }
}
