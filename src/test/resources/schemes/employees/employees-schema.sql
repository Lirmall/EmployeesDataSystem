DROP SEQUENCE IF EXISTS employees_sequence;
DROP TABLE IF EXISTS employees;

create sequence employees_sequence start with 1 increment by 1;

create table employees
(
    id      bigint,
    second_name   varchar(255),
    first_name   varchar(255),
    third_name   varchar(255),
    gender_id   bigint,
    birthday   date,
    worktype_id   bigint,
    salary   double,
    workstart_date   date,
    dismissed   boolean,
    dismissed_date   date
);