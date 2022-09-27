package ru.klokov.employeesdatasystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDTO {
    private String secondName;
    private String firstName;
    private String thirdName;

    @ApiModelProperty(value = "birthdayDate", example = "\"YYYY-MM-DD\"")
    private LocalDate birthdayDate;

    private PositionDTO positionDTO;
    private GenderDTO genderDTO;
    private RangeDTO rangeDTO;

    private LocalDate workstartDate;
}
