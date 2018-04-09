#sql("findPage")
  select di.* from mt_doctor_info di
    where 1=1 and di.dAt is NULL

    #for(x : cond)
     #if(x.key=='tel')
        and (di.tel1=#para(x.value) OR di.tel2=#para(x.value) OR di.tel3=#para(x.value))
      #else if(x.key=='name' || x.key=='email' )
        and  #(x.key) LIKE CONCAT('%',#para(x.value),'%')
      #else
         and #(x.key) #para(x.value)
      #end
    #end
    ORDER BY di.cAt,di.id DESC
#end