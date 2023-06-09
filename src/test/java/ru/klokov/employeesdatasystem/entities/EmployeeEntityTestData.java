package ru.klokov.employeesdatasystem.entities;

import java.time.LocalDate;

public class EmployeeEntityTestData {

    public static EmployeeEntity returnEmployeeEntity() {
        EmployeeEntity employee = new EmployeeEntity();

        employee.setSecondName("Testov");
        employee.setFirstName("Test");
        employee.setThirdName("Testovich");
        employee.setBirthday(LocalDate.of(2020, 10, 12));
        employee.setDismissed(false);
        employee.setDismissedDate(null);
        employee.setSalary(1000.0);
        employee.setWorktype(WorktypeEntityTestData.returnSalaryWorktypeEntity());
        employee.setGender(GenderEntityTestData.returnMaleGenderEntity());
        employee.setWorkstartDate(LocalDate.of(2020, 10, 15));

        return employee;
    }

    public static EmployeeEntity returnEmployeeEntity2() {
        EmployeeEntity employee = new EmployeeEntity();

        employee.setSecondName("Testov2");
        employee.setFirstName("Test2");
        employee.setThirdName("Testovich2");
        employee.setBirthday(LocalDate.of(2020, 11, 14));
        employee.setDismissed(false);
        employee.setDismissedDate(null);
        employee.setSalary(1002.0);
        employee.setWorktype(WorktypeEntityTestData.returnSalaryWorktypeEntity());
        employee.setGender(GenderEntityTestData.returnMaleGenderEntity());
        employee.setWorkstartDate(LocalDate.of(2020, 12, 16));

        return employee;
    }
}
