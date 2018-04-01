#sql("findPage")
  select dv.* from mt_doctor_visit dv
    where 1=1 and dv.dAt is NULL and dv.visitDate >now()
    #for(x : cond)
         and #(x.key) #para(x.value)
    #end
    ORDER BY dv.cAt,dv.id DESC
#end