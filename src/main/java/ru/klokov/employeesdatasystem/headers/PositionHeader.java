package ru.klokov.employeesdatasystem.headers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PositionHeader implements IHeader {
    NAME("Наименование должности", 40),
    WORKTYPE("Тип работы", 20),
    SALARY("Текущая зарплата", 20);

    private final String name;
    private final int width;
}
