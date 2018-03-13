#sql("findPage")
  select * from b_depot where dAt is null
    #for(x : cond)
      and #(x.key) #para(x.value)
    #end

    ORDER BY cAt DESC
#end

#sql("checkNum")
  select * from b_depot where dAt is null and cardapplyId=#para(cardapplyId)
    and ((#para(bNum) between bNum and eNum ) or (#para(eNum) between bNum and eNum) or (#para(bNum)<=bNum and #para(eNum)>=eNum))
    ORDER BY cAt DESC
#end

#sql("checkNumWithoutId")
  select * from b_depot where dAt is null and cardapplyId=#para(cardapplyId) and id<>#para(id)
    and ((#para(bNum) between bNum and eNum ) or (#para(eNum) between bNum and eNum) or (#para(bNum)<=bNum and #para(eNum)>=eNum))
    ORDER BY cAt DESC
#end



