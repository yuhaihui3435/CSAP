#sql("queryUoByCdn")
  select di.* from mt_user_ops uo  LEFT JOIN  mt_user_ops_doctor uod on uo.id=uod.uoId LEFT JOIN mt_user_ops_tax uot on uo.id=uot.id
    where 1=1 and uo.dAt is NULL and uo.ifPublic='00'
    #for(x : cond)
         #if(x.key=='tax')
            and uot.taxId in (#para(x.value))
         #else if(x.key=='dr')
            and uod.dId in (#para(x.value))
         #else
            and #(x.key) #para(x.value)
         #end
    #end
    ORDER BY uo.cAt,uo.id DESC
#end