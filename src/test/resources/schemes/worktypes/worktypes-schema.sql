DROP SEQUENCE IF EXISTS worktypes_sequence;
DROP TABLE IF EXISTS worktypes;

create sequence worktypes_sequence start with 1 increment by 1;

create table worktypes
(
    id      bigint,
    name   varchar(255)
);