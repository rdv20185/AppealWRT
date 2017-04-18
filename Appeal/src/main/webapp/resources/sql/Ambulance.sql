select
case
when f_minut03(viz_1, viz_2) <=20 then '01'
when f_minut03(viz_1, viz_2) between 21 and 40 then '02'
when f_minut03(viz_1, viz_2) between 41 and 60 then '03'
else '04' end f2 , sum(case when dtp = 1 then 0 else 1 end) f3, sum(case when dtp = 1 then 1 else 0 end) f4
from demand d, pat p 
 where caretype = 11 and d.id_demand = p.demand_id
and d.period between 201701 and 201702
group by
case
when f_minut03(viz_1, viz_2) <=20 then '01'
when f_minut03(viz_1, viz_2) between 21 and 40 then '02'
when f_minut03(viz_1, viz_2) between 41 and 60 then '03'
else '04' end
order by 1
