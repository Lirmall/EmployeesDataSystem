package ru.klokov.employeesdatasystem.headers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeHeader implements IHeader {
    SECOND_NAME("Фамилия", 20),
    FIRST_NAME("Имя", 20),
    THIRD_NAME("Отчество", 20),
    GENDER("Пол", 20),
    BIRTHDAY("Дата рождения", 20),
    SALARY("Текущая зарплата", 20),
    WORKSTART_DATE("Дата принятия на работу", 20),
    DISMISSED("Сотрудник уволен", 20),
    DISMISSED_DATE("Дата увольнения", 20);

    private final String name;
    private final int width;
}
