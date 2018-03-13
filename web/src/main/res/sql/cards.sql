#sql("findPage")
  select c.*,ct.name as cardtypeName,ca.batch as batch,sm.name as salesmenName,ct.faceVal as faceVal,d.outStatus as outStatus,ct.type as cardtypeType,(select text from s_taxonomy where id=ct.type) as cardtypeTypeTxt

    from b_cards c LEFT JOIN b_depot d on c.depotId=d.id LEFT JOIN b_cardapply ca on c.applyId=ca.id LEFT JOIN b_cardtype ct ON ca.cardtypeId=ct.id LEFT JOIN b_salesmen sm on d.salesmenId=sm.id
    LEFT JOIN b_cards_ph ph ON c.id=ph.cardsId LEFT JOIN b_cards_ip ip ON c.id=ip.cardsId LEFT JOIN b_cards_car_ph cph ON c.id=cph.cardsId LEFT JOIN b_cards_car_ip cip ON c.id=cip.cardsId
    where 1=1
    #for(x : cond)
      #if(x.key=='c.exportCode'&&x.value=='0')
         and c.exportCode is not NULL
        #else if(x.key=='c.exportCode'&&x.value=='1')
         and c.exportCode is NULL
        #else
         and #(x.key) #para(x.value)
      #end
    #end
    ORDER BY c.actAt,c.id DESC
#end

#sql("findByCode")
  select c.*,ct.name as cardtypeName,ca.batch as batch,sm.name as salesmenName,ct.faceVal as faceVal,d.outStatus as outStatus,t.text as cardtype,ct.manyPeople as manyPeople,ct.peopleCount as peopleCount,ct.protocol as protocol,ct.id as ctId,(select text from s_taxonomy where id=ct.type) as cardtypeTypeTxt
  from b_cards c LEFT JOIN b_depot d on c.depotId=d.id LEFT JOIN b_cardapply ca on c.applyId=ca.id LEFT JOIN b_cardtype ct ON ca.cardtypeId=ct.id
  LEFT JOIN b_salesmen sm on d.salesmenId=sm.id LEFT JOIN s_taxonomy t ON ct.type=t.id
  where  c.code=#para(code)
#end

#sql("findListByIds")
  select c.*,ct.name as cardtypeName,ca.batch as batch,sm.name as salesmenName,ct.faceVal as faceVal,d.outStatus as outStatus,ct.type as cardtypeType,(select text from s_taxonomy where id=ct.type) as cardtypeTypeTxt
    from b_cards c LEFT JOIN b_depot d on c.depotId=d.id LEFT JOIN b_cardapply ca on c.applyId=ca.id LEFT JOIN b_cardtype ct ON ca.cardtypeId=ct.id LEFT JOIN b_salesmen sm on d.salesmenId=sm.id
    where 1=1 and c.id in ( #para(ids) ) AND c.act='0' AND c.isLocked='0' AND c.exportCode is NULL
    ORDER BY c.actAt,c.id DESC
#end
#sql("findList")
  select c.*,ct.name as cardtypeName,ca.batch as batch,sm.name as salesmenName,ct.faceVal as faceVal,d.outStatus as outStatus,ct.type as cardtypeType,(select text from s_taxonomy where id=ct.type) as cardtypeTypeTxt

    from b_cards c LEFT JOIN b_depot d on c.depotId=d.id LEFT JOIN b_cardapply ca on c.applyId=ca.id LEFT JOIN b_cardtype ct ON ca.cardtypeId=ct.id LEFT JOIN b_salesmen sm on d.salesmenId=sm.id
    LEFT JOIN b_cards_ph ph ON c.id=ph.cardsId LEFT JOIN b_cards_ip ip ON c.id=ip.cardsId LEFT JOIN b_cards_car_ph cph ON c.id=cph.cardsId LEFT JOIN b_cards_car_ip cip ON c.id=cip.cardsId
    where 1=1
    #for(x : cond)
      #if(x.key=='c.exportCode'&&x.value=='0')
         and c.exportCode is not NULL
        #else if(x.key=='c.exportCode'&&x.value=='1')
         and c.exportCode is NULL
        #else
         and #(x.key) #para(x.value)
      #end
    #end
    ORDER BY c.actAt,c.id DESC
#end

#sql("ds00")
 select t0.id as id,t0.name as name,ifnull(t1.count,0) dCount,ifnull(t2.count,0) as uCount,ifnull(t3.count,0) as aCount,ifnull(t4.count,0) as aeCount,ifnull(t5.count,0) as eCount from
 (select id,NAME from `b_cardtype` where dAt is null
   #if(isNotBlank(ctId))
    and id=#para(ctId)
  #end
  ) t0
 left join
(select count(bc.id) as count,bca.`cardtypeId` as ctId from b_cards bc left join b_cardApply bca on bc.`applyId`=bca.`id` left join b_depot bd on bc.`depotId`=bd.`id` where bc.`depotId` is not null and bd.`outTime`>=#para(bDate) and bd.`outTime`<=#para(eDate)   group by bca.`cardtypeId`) t1 on t0.id=t1.ctId  left join
(select count(bc.id) as count,bca.`cardtypeId` as ctId from b_cards bc left join b_cardApply bca on bc.`applyId`=bca.`id` left join `b_unlock` bu on bc.`unlockId`=bu.`id` where bc.`unlockId` is not null  and bu.`cAt`>=#para(bDate) and bu.`cAt`<=#para(eDate) group by bca.`cardtypeId` ) t2 on t0.id=t2.ctId left join
(select count(bc.id) as count,bca.`cardtypeId` as ctId from b_cards bc left join b_cardApply bca on bc.`applyId`=bca.`id` where bc.`act` ='0' and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) group by bca.`cardtypeId` ) t3 on t0.id=t3.ctId left join
(select count(bc.id) as count,bca.`cardtypeId` as ctId from b_cards bc left join b_cardApply bca on bc.`applyId`=bca.`id` where bc.`act`='0' and bc.`exportAt` is not null and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`exportAt`>=#para(bDate) and bc.`exportAt`<=#para(eDate) group by bca.`cardtypeId` ) t4  on t0.id=t4.ctId left join
(select count(bc.id) as count,bca.`cardtypeId` as ctId from b_cards bc left join b_cardApply bca on bc.`applyId`=bca.`id` where bc.`act`='0' and bc.`exportAt` is null  and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) group by bca.`cardtypeId` ) t5 on t0.id=t5.ctId
#end

#sql("ds01")
select t0.id as id,t0.name as name,ifnull(t1.count,0) dCount,ifnull(t2.count,0) as uCount,ifnull(t3.count,0) as aCount ,ifnull(t4.count,0) as eCount,ifnull(t5.count,0) as aeCount from
 (select id,NAME from `b_salesmen` where dAt is null
  #if(isNotBlank(smId))
    and id=#para(smId)
  #end
  ) t0
 left join
 (select count(bc.id) as count,bs.`id` as bsId from b_salesmen bs left join b_depot bd on bd.`salesmenId`=bs.`id` left join b_cards bc on bc.`depotId`=bd.`id` where  bd.`outTime`>=#para(bDate) and bd.`outTime`<=#para(eDate)  and bc.`depotId` is not null group by bs.`id`) t1 on t0.id=t1.bsId
 left join
(select count(bc.id) as count,bs.`id` as bsId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` left join `b_unlock` bu on bc.`unlockId`=bu.`id` where bc.`unlockId` is not null  and bu.`cAt`>=#para(bDate) and bu.`cAt`<=#para(eDate) group by bs.`id` ) t2 on t0.id=t2.bsId
left join
(select count(bc.id) as count,bs.`id` as bsId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` where bc.`act` ='0' and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) group by bs.`id` ) t3 on t0.id=t3.bsId
left join
(select count(bc.id) as count,bs.`id` as bsId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` where bc.`act`='0' and  bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`exportAt` is not null and bc.`exportAt`>=#para(bDate) and bc.`exportAt`<=#para(eDate) group by bs.`id` ) t4  on t0.id=t4.bsId
left join
(select count(bc.id) as count,bs.`id` as bsId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` where bc.`act`='0' and  bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`exportAt` is null and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) group by bs.`id` ) t5 on t0.id=t5.bsId
#end

#sql("ds0100")
select t0.id as id, t0.name as name,ifnull(t1.count,0) dCount,ifnull(t2.count,0) as uCount,ifnull(t3.count,0) as aCount ,ifnull(t4.count,0) as eCount,ifnull(t5.count,0) as aeCount   from
 (select id,NAME from `b_cardtype` where dAt is null   ) t0 
 left join
 (select count(bc.id) as count,bct.`id` as ctId from  b_salesmen bs   left join b_depot bd on bd.`salesmenId`=bs.`id` left join b_cards bc on bc.`depotId`=bd.`id` left join `b_cardapply` bca on bc.`applyId`=bca.`id` left join `b_cardtype` bct on bca.`cardtypeId`=bct.`id` where  bd.`outTime`>=#para(bDate) and bd.`outTime`<=#para(eDate)   and bc.`depotId` is not null  and bs.id=#para(smId)  group by bct.`id`) t1 on t0.id=t1.ctId
 left join
(select count(bc.id) as count,bct.`id` as ctId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` left join `b_unlock` bu on bc.`unlockId`=bu.`id` left join `b_cardapply` bca on bc.`applyId`=bca.`id` left join `b_cardtype` bct on bca.`cardtypeId`=bct.`id` where bc.`unlockId` is not null  and bu.`cAt`>=#para(bDate) and bu.`cAt`<=#para(eDate) and bs.id=#para(smId)  group by bct.`id` ) t2 on t0.id=t2.ctId
left join
(select count(bc.id) as count,bct.`id` as ctId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` left join `b_cardapply` bca on bc.`applyId`=bca.`id` left join `b_cardtype` bct on bca.`cardtypeId`=bct.`id` where bc.`act` ='0' and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bs.id=#para(smId) group by bct.`id` ) t3 on t0.id=t3.ctId
left join
(select count(bc.id) as count,bct.`id` as ctId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` left join `b_cardapply` bca on bc.`applyId`=bca.`id` left join `b_cardtype` bct on bca.`cardtypeId`=bct.`id` where bc.`act`='0' and bc.`exportAt` is not null  and  bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`exportAt`>=#para(bDate) and bc.`exportAt`<=#para(eDate) and bs.id=#para(smId) group by bct.`id` ) t4  on t0.id=t4.ctId
left join
(select count(bc.id) as count,bct.`id` as ctId from b_cards bc left join b_depot bd on bc.`depotId`=bd.`id` left join b_salesmen bs on bd.`salesmenId`=bs.`id` left join `b_cardapply` bca on bc.`applyId`=bca.`id` left join `b_cardtype` bct on bca.`cardtypeId`=bct.`id` where bc.`act`='0' and  bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bc.`exportAt` is null and bc.`actAt`>=#para(bDate) and bc.`actAt`<=#para(eDate) and bs.id=#para(smId) group by bct.`id` ) t5 on t0.id=t5.ctId
#end

