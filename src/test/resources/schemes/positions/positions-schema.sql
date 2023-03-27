DROP SEQUENCE IF EXISTS position_sequence;
DROP TABLE IF EXISTS positions;

create sequence position_sequence start with 1 increment by 1;

create table positions
(
    id      bigint,
    name   varchar(255),
    worktype_id bigint,
    salary double
);