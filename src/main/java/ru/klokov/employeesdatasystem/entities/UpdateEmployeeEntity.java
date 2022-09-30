package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEmployeeEntity {
    private String secondName;
    private String firstName;
    private String thirdName;

    private LocalDate birthdayDate;

    private LocalDate updateDate;

    private PositionEntity position;

    private RangeEntity range;
}
