#sql("findPage")
  select di.* from mt_doctor_info di
    where 1=1 and dAt is NULL
    #for(x : cond)
         and #(x.key) #para(x.value)
    #end
    ORDER BY c.cAt,c.id DESC
#end