package com.yhh.csap.mt.base;

import com.jfinal.plugin.activerecord.IBean;
import com.yhh.csap.core.CoreModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseReservationRecords<M extends BaseReservationRecords<M>> extends CoreModel<M> implements IBean {

    public void setId(Integer id) {
        set("id", id);
    }

    public Integer getId() {
        return getInt("id");
    }

    public void setSeq(Integer seq) {
        set("seq", seq);
    }

    public Integer getSeq() {
        return getInt("seq");
    }

    public void setUserId(Integer userId) {
        set("userId", userId);
    }

    public Integer getUserId() {
        return getInt("userId");
    }

    public void setDvId(Integer dvId) {
        set("dvId", dvId);
    }

    public Integer getDvId() {
        return getInt("dvId");
    }

}