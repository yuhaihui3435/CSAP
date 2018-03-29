package com.yhh.csap;

import org.jsoup.safety.Whitelist;

/**
 * Created by yuhaihui8913 on 2017/11/14.
 * 常量类
 */
public interface  Consts {

     Whitelist basicWithImages=Whitelist.basicWithImages();

     String ENCRYPT_KEY="yuhaihui3435-never-late";

     String USER_ACCESS_TOKEN="userId-csap";

     int COOKIE_TIMEOUT=24*60*60;

     int COOKIE_FOREVER=24*60*60*6*365*50;

     String CURR_USER="currUser";

     String CURR_USER_ROLES="currUserRoles";

     String CURR_USER_RESES="currUserReses";

     String T_CATALOG_CK="catalog";

     String T_TAG_CK="tag";

     String BLANK="";

     String QINIU_IMG_FOLDER="csap/img/";

     String ROLE_DOCTOR="doctor";

     String ROLE_PATIENT="patient";



     enum YORN {
        yes(true), no(false);
        boolean val;

        private YORN(boolean val) {
            this.val = val;
        }

        public String getLabel() {
            return (val) ? "否" : "是";
        }

        public boolean isVal() {
            return val;
        }
    }

     enum YORN_STR {
        yes("0"), no("1");
        String val;

        private YORN_STR(String val) {
            this.val = val;
        }

        public String getLabel() {
            return (val.equals("0")) ? "是" : "否";
        }

        public String getVal() {
            return val;
        }
    }

    /**
     * @param
     * @author: 于海慧  2016/12/10
     * @Description:  状态枚举
     * @return void
     * @throws
     **/
     enum STATUS {
        enable("0"), forbidden("1");
        String val;

        private STATUS(String val) {
            this.val = val;
        }
        public String getVal(){
            return this.val;
        }
        public String getValTxt(){
            return (val.equals("0")?"正常":"禁用");
        }
    }

     enum REQ_JSON_CODE {
        success, fail,unauthorized;
    }

     enum CHECK_STATUS{
        normal("00"), waitingCheck("01"),revokeCheck("02");
        String val;

        private CHECK_STATUS(String val) {
            this.val = val;
        }
        public String getVal(){
            return this.val;
        }
        public String getValTxt(){
            if(val.equals("00")){
                return "正常";
            }else if(val.equals("01")){
                return "等待审批";
            }else if(val.equals("02")){
                return "未通过审批";
            }
            return "";
        }
    }

     enum CACHE_NAMES {
        paramCache,ssq,userRoles,user,userReses,taxonomy,art,carouselsetting,doctorTax,doctorInfo
    }

}
