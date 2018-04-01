package com.yhh.csap.mt.doctor;

import com.jfinal.core.Controller;
import com.yhh.csap.Consts;
import com.yhh.csap.core.CoreValidator;
import com.yhh.csap.kits.ResKit;
import com.yhh.csap.mt.DoctorInfo;

/**
 * 简介
 * <p>
 * 项目名称:   [csap]
 * 包:        [com.yhh.csap.mt.doctor]
 * 类名称:    [DoctorValidate]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/3/31]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class DoctorValidate extends CoreValidator {
//    private DoctorSrv doctorSrv=controller.enhance(DoctorSrv.class.getSimpleName(),DoctorSrv.class);
    @Override
    protected void validate(Controller controller) {
        String ak=getActionKey();
        if(ak.equals("/mt00/save")){
            DoctorInfo doctorInfo=controller.getModel(DoctorInfo.class,"",true);
            if(!DoctorInfo.dao.findByPropEQWithDat("email",doctorInfo.getEmail()).isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), ResKit.getMsg("DRE1"));
            }
            if(!DoctorInfo.dao.findByPropEQWithDat("tel1",doctorInfo.getTel1()).isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), ResKit.getMsg("DRE2"));
            }
        }else if(ak.equals("/mt00/update")){
            DoctorInfo doctorInfo=controller.getModel(DoctorInfo.class,"",true);

            if(!DoctorInfo.dao.findByPropEQAndIdNEQWithDat("email",doctorInfo.getEmail(),doctorInfo.getId()).isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), ResKit.getMsg("DRE1"));
            }
            if(!DoctorInfo.dao.findByPropEQAndIdNEQWithDat("tel1",doctorInfo.getTel1(),doctorInfo.getId()).isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), ResKit.getMsg("DRE2"));
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }
}
