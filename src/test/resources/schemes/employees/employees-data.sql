-- 1
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Ivanov',
             'Ivan',
             'Ivanovich',
             1,
             '1980-01-15',
             2,
             120.0,
             '2000-05-14',
             false,
             null);
-- 2
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Petrov',
             'Petr',
             'Petrovich',
             1,
             '1981-02-16',
             2,
             110.00,
             '1999-03-12',
             false,
             null);
-- 3
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Sidorov',
             'Alexander',
             'Sidorovich',
             1,
             '1982-03-17',
             2,
             130.00,
             '2001-04-16',
             false,
             null);
-- 4
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Kuznetsova',
             'Anna',
             'Ivanovna',
             2,
             '1981-02-17',
             1,
             20000.00,
             '2000-04-16',
             false,
             null);
-- 5
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Minaeva',
             'Elena',
             'Anatolyevna',
             2,
             '1990-08-15',
             1,
             20000.00,
             '2010-06-18',
             false,
             null);
-- 6
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Popov',
             'Sergey',
             'Gennadievch',
             1,
             '1975-07-18',
             1,
             18000.00,
             '1997-10-17',
             false,
             null);
-- 7
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Fomin',
             'Alexey',
             'Petrovich',
             1,
             '2000-09-18',
             1,
             20000.00,
             '2020-11-20',
             false,
             null);
-- 8
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Sazhin',
             'Alexander',
             'Vladimirovich',
             1,
             '1965-06-25',
             1,
             30000.00,
             '1980-06-08',
             false,
             null);
-- 9
insert into employees
(id,
 second_name,
 first_name,
 third_name,
 gender_id,
 birthday,
 worktype_id,
 salary,
 workstart_date,
 dismissed,
 dismissed_date)
values (next value for employees_sequence,
             'Kozlov',
             'Alexey',
             'Ivanovich',
             1,
             '1985-08-14',
             1,
             20000.00,
             '2008-08-17',
             true,
             '2022-09-02');