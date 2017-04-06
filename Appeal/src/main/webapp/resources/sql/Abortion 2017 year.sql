select lpuid,  case when  id = 0 then 'Abortion' else 'Abortion_on_MP' end , sum(cnt) ,suM(s)
from
(
select substr(ds_final,1,3) lpuid , 0 id ,  count(*) cnt, sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = hc.id),0)) s  
 from hospcard hc, demand d where
leave_d between  '01.01.2017'
and '31.12.2017'
and d.id_demand  = hc.demand_id
and d.period between 201701 and 201801
and ds_final between 'O02' and 'O07.99' -- and not  trim(ds_final) in (select mkb from abort_mkb_mz)
and substr(ds_final,1,3)  <> 'O03'
and hc.id not in (select id_pred from otkl_id)
group by substr(ds_final,1,3) 

union all

select substr(mkb,1,3) , 0 , count(*) , sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat p, demand d where
caretype = 4
and mkb between  'O02' and 'O07.99'--and not  trim(mkb) in (select mkb from abort_mkb_mz) 
and dat_end between  '01.01.2017' and '31.12.2017'
and d.id_demand  = demand_id and d.period between 201701 and 201801
and substr(mkb,1,3)  <> 'O03'
and p.id not in (select id_pred from otkl_id)
group by substr(mkb,1,3) 

union all

select substr(ds_final,1,3) , 1,  count(*), sum(summa  - nvl((select shtr_340 + shtr_zar from collect_o00 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = hc.id),0)) from hospcard hc , patinfo pi, demand d where 
leave_d between  '01.01.2017' and '31.12.2017'
and ds_final between 'O02' and 'O07.99'--  and not  trim(ds_final) in (select mkb from abort_mkb_mz)
and substr(ds_final,1,3)  <> 'O03'
and hc.id not in (select id_pred from otkl_id)
and pi.id = hc.id
and d.id_demand  = hc.demand_id
and d.period between 201701 and 201801
 and 
 (
  -------- экстренность добавлена Геленой 15.02.2017
hc.hosp_extr  = 1
------ 
or 
-------- O02
substr(ds_final,1,3)  = 'O02'
------ O04-O06
or 
( 
ds_final between 'O04' and 'O06.99'
and 
 -- changed 20.03.2017 hc.lpuid  in (1,180,100,110,187,134,103,123,607,610,618,628,634,186,140,647)
  hc.lpuid  in (1,180,100,110,187,103,123,186,140,150,607,610,618,628,634,647)
and
(

(info.mo_prik@dome_dev(trim(surname), trim(first_name), trim(sec_name), birthday,leave_d) <> hc.lpuid   and hc.lpuid like '6%'  )
or
(info.mo_prik@dome_dev(trim(surname), trim(first_name), trim(sec_name), birthday,leave_d) = hc.lpuid   and hc.lpuid  like '6%' and hc.hosp_extr  = 1)
or
(hc.lpuid  not like '6%')
)
)
) --- all
-- =============================  поликлиника  =========================================================================================
group by substr(ds_final,1,3) 

union all

select   substr(mkb,1,3)  , 0,count(*) , sum(summa  - nvl((select shtr_340 + shtr_zar from collect_o00 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat p, demand d where mes in ('215005' ,'514002' )
and dat_end  between  '01.01.2017' and '31.12.2017'
and d.id_demand  = demand_id
and d.period between 201701 and 201801
and mkb between 'O02' and 'O07.99' -- and not trim(mkb) in (select mkb from abort_mkb_mz)
and substr(mkb,1,3)  <> 'O03'
and p.id not in (select id_pred from otkl_id)
group by  substr(mkb,1,3) 

union all

(select  substr(mkb,1,3)  , 1,count(*) , sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat p, demand d where mes in ( '514002')
and mkb between 'O02' and 'O07.99' -- and not trim(mkb) in (select mkb from abort_mkb_mz)
and d.id_demand  = demand_id
and d.period between 201701 and 201801
and substr(mkb,1,3)  <> 'O03'
and p.id not in (select id_pred from otkl_id)
and dat_end  between  '01.01.2017' and '31.12.2017' group by substr(mkb,1,3) )

--------------------------
---- КУСОК за 2018 год
--------------------------
/*
union all
select substr(ds_final,1,3) lpuid , 0 id ,  count(*) cnt, sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00@link_collect2018 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = hc.id),0)) s  
 from hospcard@link_collect2018 hc, demand@link_collect2018 d where
leave_d between  '01.01.2017' and '31.12.2017' and d.id_demand  = hc.demand_id and d.period between 201701 and 201801
and ds_final between 'O02' and 'O07.99' -- and not  trim(ds_final) in (select mkb from abort_mkb_mz)
and substr(ds_final,1,3)  <> 'O03'
and hc.id not in (select id_pred from otkl_id@link_collect2018)
group by substr(ds_final,1,3) 
union all
select substr(mkb,1,3) , 0 , count(*) , sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00@link_collect2018 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat@link_collect2018 p, demand@link_collect2018 d where caretype = 4
and mkb between  'O02' and 'O07.99'--and not  trim(mkb) in (select mkb from abort_mkb_mz) 
and dat_end between  '01.01.2017' and '31.12.2017'  and d.id_demand  = demand_id and d.period between 201701 and 201801
and substr(mkb,1,3)  <> 'O03'
and p.id not in (select id_pred from otkl_id@link_collect2018)
group by substr(mkb,1,3) 
union all

select substr(ds_final,1,3) , 1,  count(*), sum(summa  - nvl((select shtr_340 + shtr_zar from collect_o00@link_collect2018 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = hc.id),0)) from hospcard@link_collect2018 hc , patinfo@link_collect2018 pi, demand@link_collect2018 d where 
leave_d between  '01.01.2017' and '31.12.2017'
and ds_final between 'O02' and 'O07.99'--  and not  trim(ds_final) in (select mkb from abort_mkb_mz)
and substr(ds_final,1,3)  <> 'O03'
and hc.id not in (select id_pred from otkl_id@link_collect2018)
 and pi.id = hc.id  and d.id_demand  = hc.demand_id and d.period between 201701 and 201801
 and 
 (
  -------- экстренность добавлена Геленой 15.02.2017
hc.hosp_extr  = 1
------ 
or 
-------- O02
substr(ds_final,1,3)  = 'O02'
------ O04-O06
or 
( 
ds_final between 'O04' and 'O06.99'
and 
 hc.lpuid  in (1,180,100,110,187,134,103,123,607,610,618,628,634,186,140,647)
and
(

(info.mo_prik@dome_dev(trim(surname), trim(first_name), trim(sec_name), birthday,leave_d) <> hc.lpuid   and hc.lpuid like '6%'  )
or
(info.mo_prik@dome_dev(trim(surname), trim(first_name), trim(sec_name), birthday,leave_d) = hc.lpuid   and hc.lpuid  like '6%' and hc.hosp_extr  = 1)
or
(hc.lpuid  not like '6%')
)
)
) --- all

group by substr(ds_final,1,3) 
union all
select   substr(mkb,1,3)  , 0,count(*) , sum(summa  - nvl((select shtr_340 + shtr_zar from collect_o00@link_collect2018 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat@link_collect2018 p, demand@link_collect2018 d where mes in ('215005' ,'514002' )
and dat_end  between  '01.01.2017' and '31.12.2017'  and d.id_demand  = demand_id and d.period between 201701 and 201801
and mkb between 'O02' and 'O07.99' -- and not trim(mkb) in (select mkb from abort_mkb_mz)
and substr(mkb,1,3)  <> 'O03'
and p.id not in (select id_pred from otkl_id@link_collect2018)
group by  substr(mkb,1,3) 
union all
(select  substr(mkb,1,3)  , 1,count(*) , sum(summa - nvl((select shtr_340 + shtr_zar from collect_o00@link_collect2018 o , medexpert.me_medexp m 
where o.id_exp = m.id  and o.id = p.id),0)) from pat@link_collect2018 p, demand@link_collect2018 d where mes in ( '514002')
and mkb between 'O02' and 'O07.99' -- and not trim(mkb) in (select mkb from abort_mkb_mz)
 and d.id_demand  = demand_id and d.period between 201701 and 201801
 and substr(mkb,1,3)  <> 'O03'
 and p.id not in (select id_pred from otkl_id@link_collect2018)
and dat_end  between  '01.01.2017' and '31.12.2017' group by substr(mkb,1,3) )
-----------------
---- конец КУСКА за 2018 год
-----------------
*/
)
group by lpuid ,id
order by 2,1