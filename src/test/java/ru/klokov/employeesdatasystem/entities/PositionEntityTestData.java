package ru.klokov.employeesdatasystem.entities;

public class PositionEntityTestData {
    public static PositionEntity returnPosition() {
        PositionEntity position = new PositionEntity();

        position.setName("TestPosition");
        position.setWorktype(WorktypeEntityTestData.returnSalaryWorktypeEntity());
        position.setSalary(1000.0);

        return position;
    }

    public static PositionEntity returnEngineerPosition() {
        PositionEntity position = new PositionEntity();

        position.setName("Engineer");
        position.setWorktype(WorktypeEntityTestData.returnSalaryWorktypeEntity());
        position.setSalary(1000.0);

        return position;
    }
}
