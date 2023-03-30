package ru.klokov.employeesdatasystem.security;

public final class Permissions {
    public static final String GENDERS_READ = "genders.read";
    public static final String GENDERS_READ_BY_ID = "genders.readById";
    public static final String GENDERS_READ_BY_FILTER = "genders.readByFilter";

    public static final String WORKTYPES_READ = "worktypes.read";
    public static final String WORKTYPES_READ_BY_ID = "worktypes.readById";
    public static final String WORKTYPES_READ_BY_FILTER = "worktypes.readByFilter";

    public static final String RANGES_READ = "ranges.read";
    public static final String RANGES_READ_BY_ID = "ranges.readById";
    public static final String RANGES_READ_BY_FILTER = "ranges.readByFilter";

    public static final String POSITIONS_READ = "positions.read";
    public static final String POSITIONS_READ_BY_ID = "positions.readById";
    public static final String POSITIONS_READ_BY_FILTER = "positions.readByFilter";
    public static final String POSITIONS_CREATE = "positions.create";
    public static final String POSITIONS_UPDATE = "positions.update";
    public static final String POSITIONS_DELETE_BY_ID = "positions.deleteById";

    public static final String EMPLOYEE_READ = "employee.read";
    public static final String EMPLOYEE_READ_BY_ID = "employee.readById";
    public static final String EMPLOYEE_READ_BY_FILTER = "employee.readByFilter";
    public static final String EMPLOYEE_CREATE = "employee.create";
    public static final String EMPLOYEE_UPDATE = "employee.update";
    public static final String EMPLOYEE_DELETE_BY_ID = "employee.dismiss";

    public static final String SALARY_GET_AVERAGE_BY_WORKTYPE = "salary.getAverageByWorktype";
    public static final String SALARY_GET_MONTH_BY_EMPLOYEE = "salary.getMonthByEmployee";
    public static final String SALARY_GET_ON_PERIOD_BY_EMPLOYEE = "salary.getOnPeriodByEmployee";

    public static final String USER_READ = "user.read";
    public static final String USER_READ_BY_ID = "user.readById";
    public static final String USER_CREATE = "user.create";
    public static final String USER_UPDATE = "user.update";
    public static final String USER_DELETE_BY_USERNAME = "user.deleteByUsername";
    public static final String USER_DELETE_BY_ID = "user.deleteById";
    public static final String USER_LOAD_BY_USERNAME = "user.loadByUsername";

    public Permissions() {
    }
}
