declare cnt integer;
begin
-- полный аглгоритм для ЭКО
-- сохраняем ЭКО

-- сбрасываем предыдущих пациентов в old для фиксации вновь поступивших 
insert into t_eco_old 	
	select  e.id,sysdate from PAT_ECO e where
	e.n_date is not null
	and
	e.id not in (select o.id from t_eco_old o);
commit; 		
	
	
	
insert into   pat_eco e
select id , fam,im,ot,dr,dat_end ,mes ,  ksg, summa , null,null,null , null,null,null, null,null,null,sysdate,null,null from pat p where ksg between '5.0' and '5.7' and caretype = 4
and p.id not in (select id from pat_eco);
commit;

-- роды из МТР (закачать из бызы firebird в таблицу reestr_17 и спросить у Маклай ID родов)
insert into pat_eco
select id , fam,im,ot,to_date(dr,'DDMMYYYY'),dat_2 ,q_u ,  '5.4', s_all , null,null,null , null,null,null, null,null,null,sysdate,null,null from ggv.reestr_17@dome_ggv
where id in (
2612981	,
2612982	,
2612983	,
2612984	,
2613164	,
2613165	,
2667323	,
2685733	,
2424333	,
2516884	,
2517103	,
2568000	,
2591214	,
2656806	,
2544064	,
2523348	,
2580352	,
2620922	,
2620957	,
2621138	,
2665540	
) and id not in (select id from pat_eco);
commit;

-- обновление информации по обращению по поводу учета беременности
for i in (
select e.*, rowid r from pat_eco e where n_id is null order by dat_end desc)
loop
    select count(*) into cnt from pat p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot and p.dr = i.dr and 
     months_between (p.dat_end,i.dat_end) between 0 and 9 and 
      mes > 100
      and caretype not in (3, 4) and 
    (
    substr(mkb,1,3) between 'Z32' and 'Z35'
    or
    (substr(mkb,1,3) between 'O10' and 'O48' and substr(mkb,1,3) not in ('O29','O31','O42'))
    )
    and not (id  in (select n_id from pat_eco e where p.id = e.n_id));
	
    if cnt > 0 then 
		update pat_eco e
		set (n_id,n_date,n_mkb,date_update) = (
			select p.id, p.dat_end, mkb, sysdate from pat p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot and p.dr = i.dr and 
			 months_between (p.dat_end,i.dat_end) between 0 and 9  and 
			  mes > 100
			 and  caretype not in (3, 4) and 
			(
			substr(mkb,1,3) between 'Z32' and 'Z35'
			or
			(substr(mkb,1,3) between 'O10' and 'O48' and substr(mkb,1,3) not in ('O29','O31','O42'))
			)
			and dat_end = 
			(
			select max(dat_end) from pat p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot and p.dr = i.dr and 
			 months_between (p.dat_end,i.dat_end) between 0 and 9 and 
			  mes > 100
			 and caretype <> 4 and 
			(
			substr(mkb,1,3) between 'Z32' and 'Z35'
			or
			(substr(mkb,1,3) between 'O10' and 'O48' and substr(mkb,1,3) not in ('O29','O31','O42'))
			)
			and  not (id  in (select n_id from pat_eco e where p.id = e.n_id))
			)
			and rownum = 1
			and  not (id  in (select n_id from pat_eco e where p.id = e.n_id))
		)
		where e.rowid = i.r;
		commit;
    end if;
end loop;
commit;

-- обновление по МТР (необходимо добавить информацию из базы МТР в таблицу reestr_17)
for i in (
select e.*, rowid r from pat_eco e where n_id is null  order by dat_end desc)
loop
    select count(*) into cnt from ggv.reestr_17@dome_ggv p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot and p.dr = to_char(i.dr,'DDMMYYYY') and 
     months_between (p.dat_2,i.dat_end) between 0 and 9 and 
    (
    substr(ds,1,3) between 'Z32' and 'Z35'
    or
    (substr(ds,1,3) between 'O10' and 'O48' and substr(ds,1,3) not in ('O29','O31','O42'))
    )
    and not (id  in (select n_id from pat_eco e where p.id = e.n_id));
    if cnt > 0 then
    update pat_eco e
    set (n_id,n_date,n_mkb) = (
    select p.id, p.dat_2, ds from ggv.reestr_17@dome_ggv p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot and p.dr = to_char(i.dr,'DDMMYYYY') and 
     months_between (p.dat_2,i.dat_end) between 0 and 9 and 
    (
    substr(ds,1,3) between 'Z32' and 'Z35'
    or
    (substr(ds,1,3) between 'O10' and 'O48' and substr(ds,1,3) not in ('O29','O31','O42'))
    )
    and not (id  in (select n_id from pat_eco e where p.id = e.n_id))
    and dat_2 = 
    (
    select max(dat_2) from ggv.reestr_17@dome_ggv p  where p.fam = i.fam and p.im = i.im and p.ot = i.ot 
    and p.dr = to_char(i.dr,'DDMMYYYY') and 
     months_between (p.dat_2,i.dat_end) between 0 and 9 and 
    (
    substr(ds,1,3) between 'Z32' and 'Z35'
    or
    (substr(ds,1,3) between 'O10' and 'O48' and substr(ds,1,3) not in ('O29','O31','O42'))
    )
   and not (id  in (select n_id from pat_eco e where p.id = e.n_id))
    )
    and rownum = 1
    )
    where e.rowid = i.r;
    commit;
    end if;
end loop;
commit;

-- обновление по родам
for i in (
select e.*, rowid r from pat_eco e where o_id is null order by dat_end desc)
loop
    select count(*) into cnt from patinfo pi ,hospcard hc  where pi.id = hc.id and upper(trim(pi.surname)) = i.fam and upper(trim(pi.first_name)) = i.im and upper(triM(pi.sec_name)) = i.ot 
    and pi.birthday = i.dr and 
     months_between (hc.arrival_d,i.dat_end) between 0 and  10 and 
    substr(upper(triM(ds_final)),1,3) between 'O60' and 'O84'
    and not (hc.id  in (select o_id from pat_eco e where hc.id = e.n_id));
   if cnt > 0 then
    update pat_eco e
    set (o_id,o_date,o_mkb,date_update) = (
    select hc.id, hc.leave_d, upper(trim(ds_final)), sysdate from patinfo pi ,hospcard hc  where pi.id = hc.id and upper(trim(pi.surname)) = i.fam and upper(trim(pi.first_name)) = i.im and upper(triM(pi.sec_name)) = i.ot 
    and pi.birthday = i.dr and 
     months_between (hc.arrival_d,i.dat_end) between 0 and  10 and 
    substr(upper(triM(ds_final)),1,3) between 'O60' and 'O84'
     and not (hc.id  in (select o_id from pat_eco e where hc.id = e.n_id))
    and rownum = 1
    )
    where e.rowid = i.r;
    commit;
    end if;
end loop;
commit;

end;