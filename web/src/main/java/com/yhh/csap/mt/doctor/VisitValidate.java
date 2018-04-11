package com.yhh.csap.mt.doctor;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreValidator;
import com.yhh.csap.kits.ResKit;
import com.yhh.csap.mt.DoctorVisit;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.doctor]
 * 类名称:    [VisitValidate]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/31]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class VisitValidate extends CoreValidator {
    private DoctorSrv doctorSrv = Duang.duang(DoctorSrv.class.getSimpleName(),DoctorSrv.class);
    @Override
    protected void validate(Controller controller) {
        String ak=getActionKey();
        if(ak.equals("/mt01/save")){
            DoctorVisit doctorVisit=controller.getModel(DoctorVisit.class,"",true);
            if(doctorSrv.findVisitByDRIdAndVDate(doctorVisit.getDId(),doctorVisit.getVisitDate())!=null){
                addError(Consts.REQ_JSON_CODE.fail.name(), ResKit.getMsg("DRVE1"));
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {

            controller.renderJson(getErrorJSON());
    }
}
