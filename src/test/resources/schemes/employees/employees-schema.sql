DROP SEQUENCE IF EXISTS employees_sequence;
DROP TABLE IF EXISTS employees;

create sequence employees_sequence start with 1 increment by 1;

create table employees
(
    id      bigint primary key,
    second_name   varchar(255),
    first_name   varchar(255),
    third_name   varchar(255),
    gender_id   bigint, constraint gender foreign key (gender_id) references genders(id),
    birthday   date,
    worktype_id   bigint, constraint worktype_id foreign key (worktype_id) references worktypes(id),
    salary   double,
    workstart_date   date,
    dismissed   boolean,
    dismissed_date   date
);