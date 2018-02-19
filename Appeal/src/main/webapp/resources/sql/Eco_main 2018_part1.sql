select
 case when  id = 1 then 'pred'
      else
          case when id = 2 then 'pred_mtr'
          else
              case when id = 3 then 'this_year'
                else
                    case when id = 4 then 'this_year_mtr' end              
              end              
          end
 end,
 cnt
from(
select 1 id,count(*) as cnt
--select *
from PAT_ECO e
where
trunc(n_date) between date_ot and date_to
-- встали на учет в женскую консультацию по ведению беременности в отчетном периоде, прошедшие процедуру эко передшествующему отчетному перриоду
and to_number(to_char(e.dat_end,'YYYY')) < to_number(to_char(e.n_date,'YYYY'))
-- За 2016 год актуальные КСГ 5.5???  ???
-- За 2017 год актуальные КСГ 5.5??? 5.4
-- За 2018 год актуальные КСГ 5.7 5.6 5.4
-- Зачем этот мес ???
and (
(e.ksg = '5.5' and e.dat_end < '01.01.2017')
or (e.ksg = '5.4' and e.dat_end >= '01.01.2017')
-- мес убрали актуален был 
--or (e.mes = '814005')
or ( (e.ksg = '5.4' or e.ksg = '5.6' or e.ksg = '5.7') and e.dat_end >= '01.01.2018')
)
-- исключаем МТР  TODO: поменять добавив поле-флаг. На данный момент те id меньше 3_000_000 это мтр
and e.n_id > 3000000

union

select  2 id,count(*) as cnt
--select *
from PAT_ECO e
where
trunc(n_date) between date_ot and date_to
and to_number(to_char(e.dat_end,'YYYY')) < to_number(to_char(e.n_date,'YYYY'))
and
-- МТР
e.n_id < 3000000
and (
(e.ksg = '5.5' and e.dat_end < '01.01.2017')
or (e.ksg = '5.4' and e.dat_end >= '01.01.2017')
--or (e.mes = '814005')
or ( (e.ksg = '5.4' or e.ksg = '5.6' or e.ksg = '5.7') and e.dat_end >= '01.01.2018')
)

union
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------------  this year --------------
select  3 id,count(*) as cnt
--select *
from PAT_ECO e
where
trunc(n_date) between date_ot and date_to
-- встали на учет в женскую консультацию по ведению беременности в отчетном периоде, прошедшие процедуру эко в отчетном периоде
and to_number(to_char(e.dat_end,'YYYY')) = to_number(to_char(e.n_date,'YYYY'))
and
-- МТР
e.n_id > 3000000
and (
(e.ksg = '5.5' and e.dat_end < '01.01.2017')
or (e.ksg = '5.4' and e.dat_end >= '01.01.2017')
--or (e.mes = '814005')
or ( (e.ksg = '5.4' or e.ksg = '5.6' or e.ksg = '5.7') and e.dat_end >= '01.01.2018')
)

union 

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------------  this year_mtr --------------
select  4 id,count(*) as cnt
--select *
from PAT_ECO e
where
trunc(n_date) between date_ot and date_to
-- встали на учет в женскую консультацию по ведению беременности в отчетном периоде, прошедшие процедуру эко в отчетном периоде
and to_number(to_char(e.dat_end,'YYYY')) = to_number(to_char(e.n_date,'YYYY'))
and
-- МТР
e.n_id < 3000000
and (
(e.ksg = '5.5' and e.dat_end < '01.01.2017')
or (e.ksg = '5.4' and e.dat_end >= '01.01.2017')
--or (e.mes = '814005')
or ( (e.ksg = '5.4' or e.ksg = '5.6' or e.ksg = '5.7') and e.dat_end >= '01.01.2018')
)




)
