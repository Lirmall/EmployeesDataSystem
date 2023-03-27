DROP SEQUENCE IF EXISTS ranges_sequence;
DROP TABLE IF EXISTS ranges;

create sequence ranges_sequence start with 1 increment by 1;

create table ranges
(
    id      bigint,
    name   varchar(255),
    bonus double
);