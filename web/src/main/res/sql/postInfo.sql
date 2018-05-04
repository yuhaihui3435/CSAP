#sql("findPage")
  select p.* from www_post_info p LEFT JOIN s_user u ON p.operId=u.id
    where 1=1 and p.dAt is NULL and p.checkStatus='00' and p.status='0'
    #for(x : cond)
          and #(x.key) #para(x.value)
    #end
    ORDER BY p.ifTop ASC,#(order)
#end