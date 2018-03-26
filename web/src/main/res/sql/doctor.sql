#sql("findPage")
  select di.* from mt_doctor_info di
    where 1=1 and dAt is NULL
      #if(isNotBlank(tel))
        and (di.tel1=#para(tel) OR di.tel2=#para(tel) OR di.tel3=#para(tel))
      #end
    #for(x : cond)
         and #(x.key) #para(x.value)
    #end
    ORDER BY c.actAt,c.id DESC
#end