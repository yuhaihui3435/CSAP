package com.yhh.csap.mt.base;

import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserMedicalrecordsDoctor<M extends BaseUserMedicalrecordsDoctor<M>> extends CoreModel<M> implements IBean {

    public void setId(Integer id) {
        set("id", id);
    }

    public Integer getId() {
        return getInt("id");
    }

    public void setUmId(Integer umId) {
        set("umId", umId);
    }

    public Integer getUmId() {
        return getInt("umId");
    }

    public void setDId(Integer dId) {
        set("dId", dId);
    }

    public Integer getDId() {
        return getInt("dId");
    }

}
