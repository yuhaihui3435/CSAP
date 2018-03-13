
#sql("findCardIpCountByCertCode")
  select count(*) as actCount from b_cards_ip where idCard=#para(0)
#end