DROP SEQUENCE IF EXISTS genders_sequence;
DROP TABLE IF EXISTS genders;

create sequence genders_sequence start with 1 increment by 1;

create table genders
(
    id      bigint primary key,
    name   varchar(255)
);