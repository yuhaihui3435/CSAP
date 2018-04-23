package com.yhh.csap.mt.doctor;

import cn.hutool.core.util.StrUtil;
import com.yhh.csap.Consts;
import com.yhh.csap.mt.DoctorInfo;
import com.yhh.csap.mt.DoctorVisit;


import java.util.Date;
import java.util.List;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.doctor]
 * 类名称:    [DoctorSrv]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/26]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class DoctorSrv {

    public DoctorVisit findVisitByDRIdAndVDate(int drId,Date vDate){
        String sql="select * from mt_doctor_visit where dId=? and visitDate=? and dAt is null";
        return DoctorVisit.dao.findFirst(sql,drId,vDate);
    }

    public List<DoctorInfo> findByNameLike(String name){
        StringBuffer sql=new StringBuffer("select * from mt_doctor_info where dAt is null   ");
        if(StrUtil.isNotBlank(name)){
            sql.append(" and name like ?");
            return DoctorInfo.dao.findByCache(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike"+name,sql.toString(),"%"+name+"%");
        }else{
            return DoctorInfo.dao.findByCache(Consts.CACHE_NAMES.doctorInfo.name(),"findByNameLike",sql.toString());
        }
    }

    public List<DoctorVisit> findByDIdAndLimitRecentCache(int dId,int limit){
       return DoctorVisit.dao.findByDIdAndLimitRecentCache(dId,limit);
    }



}
