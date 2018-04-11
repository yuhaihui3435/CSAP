package com.yhh.csap.core;


import com.jfinal.plugin.activerecord.Model;
import com.yhh.csap.Consts;
import org.jsoup.Jsoup;

import java.util.List;
import java.util.Map;

/**
 * Created by 于海慧（125227112@qq.com） on 2016/11/30.
 */
public abstract class CoreModel<M extends CoreModel<M>> extends Model<M> {

    /**
     * 防止xss攻击处理
     * @return
     */
    @Override
    public boolean save(){
        for(Map.Entry me : _getAttrs().entrySet()){
            if(me.getValue() instanceof String) {
                set((String) me.getKey(), Jsoup.clean((String) me.getValue(), Consts.basicWithImages));
            }
        }
        return super.save();
    }

    public boolean saveWithoutClean(){
        return super.save();
    }

    /**
     * 防止xss攻击处理
     * @return
     */
    @Override
    public boolean update(){
        for(Map.Entry me : _getAttrs().entrySet()){
            if(me.getValue() instanceof String)
                set((String)me.getKey(), Jsoup.clean((String)me.getValue(),Consts.basicWithImages));
        }
        return super.update();
    }

    public boolean updateWithoutClean(){
        return super.update();
    }

    public String getYOrNTxt(boolean val){
        return (val)? Consts.YORN.yes.getLabel(): Consts.YORN.no.getLabel();
    }

    public String getStatusTxt(String val){
        if(val==null)return "";
        return (val.equals(Consts.STATUS.enable.getVal())? Consts.STATUS.enable.getValTxt():val.equals(Consts.STATUS.forbidden.getVal())? Consts.STATUS.forbidden.getValTxt():Consts.STATUS.expired.getValTxt());
    }

    public List<M> findByPropEQ(String name, Object val){
        return super.find("select * from "+getTableName()+" where "+name+"=?",val);
    }
    public List<M> findByPropEQWithDat(String name, Object val){
        return super.find("select * from "+getTableName()+" where "+name+"=? and dAt is null",val);
    }
    public M findFristByPropEQ(String name,Object val){
        return super.findFirst("select * from "+getTableName()+" where "+name+"=?",val);
    }
    public List<M> findByPropEQAndIdNEQ(String name, Object val,Object id){
        return super.find("select * from "+getTableName()+" where "+name+"=? and id!=?",val,id);
    }
    public List<M> findByPropEQAndIdNEQWithDat(String name, Object val,Object id){
        return super.find("select * from "+getTableName()+" where "+name+"=? and id!=? and dAt is null",val,id);
    }


    public List<M> findByPropLIKE(String name, String val){
        return super.find("select * from "+getTableName()+" where "+name+" like ?","%"+val+"%");
    }

    public List<M> findAll(){
        return super.find("select * from "+getTableName());
    }

    public abstract String getTableName();

}
