package ru.klokov.employeesdatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Long id;

    private String name;

    private WorktypeDTO worktype;

    private Double salary;
}
