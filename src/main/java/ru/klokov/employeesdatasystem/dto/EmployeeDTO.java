package ru.klokov.employeesdatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    private String secondName;

    private String firstName;

    private String thirdName;

    private GenderDTO gender;

    private LocalDate birthday;

    private WorktypeDTO worktype;

    private Double salary;

    private LocalDate workstartDate;

    private Boolean dismissed;

    private LocalDate dismissedDate;
}
