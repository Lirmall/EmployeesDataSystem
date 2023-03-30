DROP SEQUENCE IF EXISTS employee_position_range_sequence;
DROP TABLE IF EXISTS employee_position_range;

create sequence employee_position_range_sequence start with 1 increment by 1;

create table employee_position_range
(
    id      bigint primary key,
    employee_id   bigint, constraint employee_id foreign key (employee_id) references employees(id),
    position_id bigint, constraint position_id foreign key (position_id) references positions(id),
    position_range bigint, constraint position_range foreign key (position_range) references ranges(id),
    position_change_date date
);