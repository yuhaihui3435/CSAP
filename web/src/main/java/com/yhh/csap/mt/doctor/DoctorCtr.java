package com.yhh.csap.mt.doctor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.yhh.csap.Consts;
import com.yhh.csap.admin.model.Taxonomy;
import com.yhh.csap.admin.model.User;
import com.yhh.csap.admin.user.UserSrv;
import com.yhh.csap.core.CoreController;
import com.yhh.csap.core.CoreException;
import com.yhh.csap.kits.AppKit;
import com.yhh.csap.kits.PinyinKit;
import com.yhh.csap.kits.QiNiuKit;
import com.yhh.csap.kits.ext.BCrypt;
import com.yhh.csap.mt.DoctorInfo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.randomString;
import static cn.hutool.core.util.StrUtil.UNDERLINE;
import static cn.hutool.core.util.StrUtil.reverse;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.doctor]
 * 类名称:    [DoctorCtr]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/26]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class DoctorCtr extends CoreController {

    private UserSrv userSrv=enhance(UserSrv.class.getSimpleName(),UserSrv.class);

    public void dr(){
        //疾病列表
        List<Taxonomy> diseaseList= CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"diseaseList");
        //手术方式
        List<Taxonomy> opModelList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"opModelList");
        //职称
        List<Taxonomy> drTitleList=CacheKit.get(Consts.CACHE_NAMES.taxonomy.name(),"drTitleList");
        Map<String,List<Taxonomy>> map=new HashMap<>();
        map.put("diseaseList",diseaseList);
        map.put("opModelList",opModelList);
        map.put("drTitleList",drTitleList);
        renderJson(map);
    }


    public void list(){
        String name=getPara("name");
        String email=getPara("email");
        String sex=getPara("sex");
        String tel=getPara("tel");
        String status=getPara("status");
        Page page=null;
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(name))
            kv.put("instr(di.name,",name+")>0");
        if(StrUtil.isNotBlank(email))
            kv.put(" instr(di.email,",email+")>0");
        if(StrUtil.isNotBlank(sex))
            kv.put(" di.sex=",sex);
        if(StrUtil.isNotBlank(tel))
            kv.put("tel",tel);
        if(StrUtil.isNotBlank(status))
            kv.put("status=",status);

        SqlPara sqlPara= Db.getSqlPara("doctor.findPage",Kv.by("cond",kv));
        page=DoctorInfo.dao.paginate(getPN(),getPS(),sqlPara);
        renderJson(page);
    }
    @Before({DoctorValidate.class,Tx.class})
    public void save(){
        DoctorInfo doctorInfo=getModel(DoctorInfo.class,"",true);
        doctorInfo.setCAt(new Date());
        doctorInfo.setOperId(currUser()==null?null:currUser().getId().intValue());
        boolean bl=true;
        StringBuilder loginname=new StringBuilder().append(PinyinKit.getFullSpell(doctorInfo.getName())).append(reverse(doctorInfo.getTel1()).trim().substring(0,4));
        String email=doctorInfo.getEmail();
        String tel=doctorInfo.getTel1();
        while (bl) {
            try {
                userSrv.checkLoginnameAndEmailAndTel(loginname.toString(),email,tel);
                bl=false;
            }catch (CoreException ce){
                if (ce.getCode().equals("CE1")){
                    if(loginname.toString().contains(UNDERLINE)){
                        String s=loginname.toString().split(UNDERLINE)[0];
                        loginname.delete(0,loginname.length());
                        loginname.append(s);
                    }
                    loginname.append(UNDERLINE+randomString(4));
                }else if(ce.getCode().equals("CE2")){
                    email=null;
                }else if(ce.getCode().equals("CE3")){
                    tel=null;
                }
            }
        }

        String imgBase64Data=getPara("imgBase64Data");
        if(StrUtil.isNotBlank(imgBase64Data)){
            String imgType= AppKit.getBase64ImgType(imgBase64Data);
            String imgName= DateUtil.current(true)+"";
            String savePath=Consts.QINIU_IMG_FOLDER+"dr/"+imgName+"."+imgType;
            try {
                String qnRs=QiNiuKit.put64image(imgBase64Data,savePath);
                if(qnRs==null) {
                    LogKit.error("上传头像失败");
                }else{
                    if(qnRs.equals(Consts.YORN_STR.yes.name())){
                        doctorInfo.setAvatar(CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),"qn_url")+savePath);
                    }else{
                        LogKit.error("base64上传失败:"+qnRs);
                    }
                }
            } catch (IOException e) {
                LogKit.error("图片上传失败，原因："+e.getMessage());
            }
        }
        User user = new User();
        user.setCAt(new Date());
        user.setLoginname(loginname.toString());
        user.setEmail(email);
        user.setCAt(new Date());
        user.setStatus(Consts.YORN_STR.yes.getVal());
        user.setNickname(doctorInfo.getName());
        user.setIsAdmin(Consts.YORN_STR.no.getVal());
        user.setAvatar(doctorInfo.getAvatar());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(BCrypt.hashpw(PinyinKit.getFullSpell(doctorInfo.getName()), user.getSalt()));
        user.setPhone(tel);
        user.setAvatar(doctorInfo.getAvatar());
        userSrv.addDr(user);
        doctorInfo.setUserId(user.getId().intValue());
        doctorInfo.save();
        CacheKit.put(Consts.CACHE_NAMES.doctorInfo.name(),"id_".concat(doctorInfo.getId().toString()),doctorInfo);
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike"+doctorInfo.getName());
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike");
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"drList");
        renderSuccessJSON("医生信息创建成功");
    }
    @Before({DoctorValidate.class,Tx.class})
    public void update(){
        DoctorInfo doctorInfo=getModel(DoctorInfo.class,"",true);
        doctorInfo.setMAt(new Date());
        doctorInfo.setOperId(currUser()==null?null:currUser().getId().intValue());
        doctorInfo.update();
        CacheKit.removeAll(Consts.CACHE_NAMES.doctorTax.name());
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"id_".concat(doctorInfo.getId().toString()));
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike"+doctorInfo.getName());
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike");
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"drList");
        renderSuccessJSON("医生信息更新成功");
    }

    public void del(){
        int integer=getParaToInt("id");
        DoctorInfo doctorInfo=DoctorInfo.dao.findById(integer);
        doctorInfo.setDAt(new Date());
        doctorInfo.setOperId(currUser()==null?null:currUser().getId().intValue());
        doctorInfo.update();
        CacheKit.removeAll(Consts.CACHE_NAMES.doctorTax.name());
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"id_".concat(doctorInfo.getId().toString()));
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike"+doctorInfo.getName());
        CacheKit.remove(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike");
        CacheKit.remove(Consts.CACHE_NAMES.index.name(),"drList");
        renderSuccessJSON("医生信息删除成功");
    }

    public void view(){
        int id=getParaToInt("id");
        renderJson(DoctorInfo.dao.findFirstByCache(Consts.CACHE_NAMES.doctorInfo.name(),"id_"+id,"select * from mt_doctor_info where id=? ",id));
    }



}
