package com.yhh.csap.www.carouselsetting;

import com.jfinal.aop.Before;
import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ImageUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xiaoleilu.hutool.util.URLUtil;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Role;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.kits.AppKit;
import com.yhh.csap.kits.QiNiuKit;
import com.yhh.csap.www.model.CarouselSetting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        StringBuffer where =new StringBuffer("from "+ CarouselSetting.dao.getTableName()+" cs where 1=1 and dAt is null ");
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
        String imgBase64Data=getPara("imgBase64Data");
        String imgType= AppKit.getBase64ImgType(imgBase64Data);
        String imgName= DateUtil.current(true)+"";
//        BufferedImage bufferedImage=ImageUtil.toImage(imgBase64Data);
//        File file=FileUtil.file(PathKit.getWebRootPath()+"/WEB-INF/tmp/"+imgName+"."+imgType);
//        boolean bl=AppKit.base64ToImg(imgBase64Data,file);
//        if(!bl){
//            file.delete();
//            LogKit.error("base64转图片失败");
//            renderFailJSON("图片上传处理失败，请重试");
//            return;
//        }
        String savePath=Consts.QINIU_IMG_FOLDER+"cls/"+imgName+"."+imgType;
        try {
            String qnRs=QiNiuKit.put64image(imgBase64Data,savePath);
            if(qnRs==null) {
                renderFailJSON("图片上传失败");
                return ;
            }else{
                if(qnRs.equals(Consts.YORN_STR.yes.name())){
                    carouselSetting.setPic(CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),"qn_url")+savePath);
                }else{
                    LogKit.error("base64上传失败:"+qnRs);
                    renderFailJSON("图片上传失败");
                    return ;
                }
            }
        } catch (IOException e) {
            LogKit.error("图片上传失败，原因："+e.getMessage());
            renderFailJSON("图片上传失败，请重试。");
            return;
        }
        carouselSetting.save();
        CacheKit.removeAll(Consts.CACHE_NAMES.carouselsetting.name());
        renderSuccessJSON("轮播数据新增成功","");
    }
    @Before({ClsValidator.class,Tx.class})
    public void update(){
        CarouselSetting carouselSetting=getModel(CarouselSetting.class,"",true);
        carouselSetting.setOperId(currUser()==null?null:currUser().getId().intValue());
        carouselSetting.setMAt(new Date());
        String imgBase64Data=getPara("imgBase64Data");
        if(StrUtil.isNotBlank(imgBase64Data)) {
            String imgType = AppKit.getBase64ImgType(imgBase64Data);
            String imgName = DateUtil.current(true) + "";
            String savePath = Consts.QINIU_IMG_FOLDER + "cls/" + imgName + "." + imgType;
            try {
                String qnRs = QiNiuKit.put64image(imgBase64Data, savePath);
                if (qnRs == null) {
                    renderFailJSON("图片上传失败");
                    return;
                } else {
                    if (qnRs.equals(Consts.YORN_STR.yes.name())) {
                        carouselSetting.setPic(CacheKit.get(Consts.CACHE_NAMES.paramCache.name(), "qn_url") + savePath);
                    } else {
                        LogKit.error("base64上传失败:" + qnRs);
                        renderFailJSON("图片上传失败");
                        return;
                    }
                }
            } catch (IOException e) {
                LogKit.error("图片上传失败，原因：" + e.getMessage());
                renderFailJSON("图片上传失败，请重试。");
                return;
            }
        }
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
        map.put("areasList",CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"carouselSettingAreasList"));
        renderJson(map);
    }

}
