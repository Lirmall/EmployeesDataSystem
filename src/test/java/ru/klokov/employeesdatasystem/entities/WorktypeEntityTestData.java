package ru.klokov.employeesdatasystem.entities;

public class WorktypeEntityTestData {

    public static WorktypeEntity returnSalaryWorktypeEntity() {
        WorktypeEntity worktype = new WorktypeEntity();

        worktype.setId(1L);
        worktype.setName("Salary");

        return worktype;
    }

    public static WorktypeEntity returnHourlyWorktypeEntity() {
        WorktypeEntity worktype = new WorktypeEntity();

        worktype.setId(2L);
        worktype.setName("Hourly");

        return worktype;
    }

    public static WorktypeEntity returnTestWorktypeEntity() {
        WorktypeEntity worktype = new WorktypeEntity();

        worktype.setName("Test");

        return worktype;
    }
}
