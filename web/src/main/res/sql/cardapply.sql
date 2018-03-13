#sql("findPage")
  select * from b_cardapply where dAt is null
    #for(x : cond)
      and #(x.key) #para(x.value)
    #end

    ORDER BY cAt DESC
#end

#sql("findEnableList")
  select * from b_cardapply where dAt is null
    #for(x : cond)
      #if(x.key.indexOf('like')>-1)
        and #(x.key) %#para(x.value)%
      #else
        and #(x.key) #para(x.value)
      #end
    #end
    and checkStatus='00' and exeCard='0' and status='0'
    ORDER BY cAt DESC
#end