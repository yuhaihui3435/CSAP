package com.yhh.csap.core;

import com.jfinal.kit.StrKit;
import com.yhh.csap.kits.ResKit;

/**
 * Created by yuhaihui8913 on 2016/12/6.
 */
public class CoreException extends RuntimeException {
    private String code="999";
    private String msg;

    /**
     * 可以直接传入msg配置中的key，如果匹配不到key，则直接输出
     * @param str
     */
    public CoreException(String str){
        super(StrKit.isBlank(ResKit.getMsg(str))?str:ResKit.getMsg(str));
        this.msg=StrKit.isBlank(ResKit.getMsg(str))?str:ResKit.getMsg(str);
        this.code=str;
    }




    public CoreException(String code, String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
