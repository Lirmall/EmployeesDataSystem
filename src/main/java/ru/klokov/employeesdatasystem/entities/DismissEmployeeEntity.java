package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DismissEmployeeEntity {
    private String secondName;
    private String firstName;
    private String thirdName;

    private LocalDate birthdayDate;

    private LocalDate workstartDate;

    private LocalDate dismissDate;
}
