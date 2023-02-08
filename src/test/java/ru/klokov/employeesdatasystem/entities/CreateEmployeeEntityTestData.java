package ru.klokov.employeesdatasystem.entities;

import java.time.LocalDate;

public class CreateEmployeeEntityTestData {
    public static CreateEmployeeEntity returnCreateEmployeeEntity() {
        CreateEmployeeEntity entity = new CreateEmployeeEntity();

        entity.setSecondName("Testov");
        entity.setFirstName("Test");
        entity.setThirdName("Testovich");
        entity.setPosition(PositionEntityTestData.returnEngineerPosition());
        entity.setRange(RangeEntityTestData.returnNoRangeEntity());
        entity.setBirthdayDate(LocalDate.of(2020, 5, 15));
        entity.setGender(GenderEntityTestData.returnMaleGenderEntity());
        entity.setWorkstartDate(LocalDate.of(2022, 9, 28));

        return entity;
    }
}
