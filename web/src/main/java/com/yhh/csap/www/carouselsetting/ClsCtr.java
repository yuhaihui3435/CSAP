package com.yhh.csap.www.carouselsetting;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Role;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.www.model.CarouselSetting;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.www.carouselsetting]
 * 类名称:    [ClsCtr]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/15]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class ClsCtr extends CoreController {

    public void list(){
        String search=getPara("search");
        StringBuffer where =new StringBuffer("from "+ CarouselSetting.dao.getTableName()+" cs where 1=1 and status='0' ");
        Page page=null;
        if(!isParaBlank("search")){
            where.append(" and instr(cs.name,?)>0");
            page= CarouselSetting.dao.paginate(getPN(),getPS(),"select * ",where.toString(),search);
        }else{
            page=CarouselSetting.dao.paginate(getPN(),getPS(),"select * ",where.toString());
        }
        renderJson(page);
    }

    @Before({ClsValidator.class,Tx.class})
    public void save(){
        CarouselSetting carouselSetting=getModel(CarouselSetting.class,"",true);
        carouselSetting.setOperId(currUser()==null?null:currUser().getId().intValue());
        carouselSetting.setCAt(new Date());
        carouselSetting.setMAt(new Date());
        carouselSetting.setStatus(Consts.STATUS.enable.getVal());
        carouselSetting.save();
        CacheKit.removeAll(Consts.CACHE_NAMES.carouselsetting.name());
        renderSuccessJSON("轮播数据新增成功","");
    }
    @Before({ClsValidator.class,Tx.class})
    public void update(){
        CarouselSetting carouselSetting=getModel(CarouselSetting.class,"",true);
        carouselSetting.setOperId(currUser()==null?null:currUser().getId().intValue());
        carouselSetting.setMAt(new Date());
        carouselSetting.update();
        CacheKit.removeAll(Consts.CACHE_NAMES.carouselsetting.name());
        renderSuccessJSON("轮播数据更新成功","");
    }
    @Before(Tx.class)
    public void del(){
        if(isParaExists("ids")){
            String ids=getPara("ids");
            String[] _dis=ids.split(",");
            int id=0;
            for(String s:_dis) {
                id=Integer.parseInt(s);
                CarouselSetting.dao.deleteById(id);

            }
            CacheKit.removeAll(Consts.CACHE_NAMES.carouselsetting.name());
            renderSuccessJSON("轮播数据删除成功","");


        }else{
            renderFailJSON("轮播删除失败,没有得到数据内容","");
        }
    }

    /**
     *
     * 准备数据
     *
     */
    public void dr(){
        Map<String,Object> map=new HashMap<>();
        map.put("areasList",CacheKit.get(Consts.CACHE_NAMES.carouselsetting.name(),"carpuselSettingAreasList"));
        renderJson(map);
    }

}
