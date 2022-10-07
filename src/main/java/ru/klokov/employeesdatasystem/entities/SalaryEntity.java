package ru.klokov.employeesdatasystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryEntity {
    private String nameOfSalary;
    private Double salary;
}
