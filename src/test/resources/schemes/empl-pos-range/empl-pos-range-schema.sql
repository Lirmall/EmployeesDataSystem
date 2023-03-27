DROP SEQUENCE IF EXISTS employee_position_range_sequence;
DROP TABLE IF EXISTS employee_position_range;

create sequence employee_position_range_sequence start with 1 increment by 1;

create table employee_position_range
(
    id      bigint,
    employee_id   bigint,
    position_id bigint,
    position_range bigint,
    position_change_date date
);