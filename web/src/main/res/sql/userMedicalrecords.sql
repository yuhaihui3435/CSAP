#sql("queryUmByCdn")
  select di.* from mt_user_medicalrecords um  LEFT JOIN  mt_user_medicalrecords_doctor umd on um.id=umd.umId LEFT JOIN mt_user_medicalrecords_tax umt on um.id=umt.id
    where 1=1 and um.dAt is NULL and um.ifPublic='00'
    #for(x : cond)
         #if(x.key=='tax')
            and umt.taxId in (#para(x.value))
         #else if(x.key=='dr')
            and umd.dId in (#para(x.value))
         #else
            and #(x.key) #para(x.value)
         #end
    #end
    ORDER BY um.cAt,um.id DESC
#end