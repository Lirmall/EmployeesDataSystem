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
public class UpdateEmployeeDTO {
    private String secondName;
    private String firstName;
    private String thirdName;

    @ApiModelProperty(value = "birthdayDate", example = "\"YYYY-MM-DD\"")
    private LocalDate birthdayDate;

    @ApiModelProperty(value = "updateDate", example = "\"YYYY-MM-DD\"")
    private LocalDate updateDate;

    private PositionDTO positionDTO;

    private RangeDTO rangeDTO;
}
