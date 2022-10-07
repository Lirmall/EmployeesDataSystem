package ru.klokov.employeesdatasystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPeriodEntity {
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private Double multiplier;
    private int workdaysForSalaryWorktype;

    public SalaryPeriodEntity(LocalDate periodStart, LocalDate periodEnd, Double multiplier) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.multiplier = multiplier;
    }
}
