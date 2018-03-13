#sql("findList")
    select * from b_salesmen bs where dAt is null
      #for(x : cond)
          #if(x.key!='search')
              and #(x.key) #para(x.value)
          #end
          #if(x.key=='search')
              and (instr(bs.email,#para(x.value))>0 or instr(bs.phone,#para(x.value))>0 or instr(bs.name,#para(x.value))>0 or instr(bs.name,#para(x.value))>0)
          #end
      #end
      ORDER BY cAt DESC

#end