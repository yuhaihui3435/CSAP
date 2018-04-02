#sql("findPage")
  select di.* from mt_doctor_info di
    where 1=1 and di.dAt is NULL

    #for(x : cond)
     #if(isNotBlank(tel))
        and (di.tel1=#para(tel) OR di.tel2=#para(tel) OR di.tel3=#para(tel))
      #else
         and #(x.key) #para(x.value)
      #end
    #end
    ORDER BY di.cAt,di.id DESC
#end