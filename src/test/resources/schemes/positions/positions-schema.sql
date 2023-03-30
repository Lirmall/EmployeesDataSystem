DROP SEQUENCE IF EXISTS position_sequence;
DROP TABLE IF EXISTS positions;

create sequence position_sequence start with 1 increment by 1;

create table positions
(
    id      bigint primary key,
    name   varchar(255),
    worktype_id bigint, constraint worktype foreign key (worktype_id) references worktypes(id),
    salary double
);