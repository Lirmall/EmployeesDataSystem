package ru.klokov.employeesdatasystem.specifications.employeeSpecification;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.klokov.employeesdatasystem.specifications.AbstractDictionarySearchModel;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeSearchModel extends AbstractDictionarySearchModel {
    List<Long> ids;

    List<String> secondNames;

    List<String> firstNames;

    List<String> thirdNames;

    List<Long> genderIds;

    @ApiModelProperty(value = "birthdayDates", example = "[\"YYY-MM-DD\"]")
    List<String> birthdayDates;

    List<Long> worktypeIds;

    List<Double> salaries;

    @ApiModelProperty(value = "workstartDates", example = "[\"YYY-MM-DD\"]")
    List<String> workstartDates;

    List<Boolean> dismissed;

    @ApiModelProperty(value = "dismissedDates", example = "[\"YYY-MM-DD\"]")
    List<String> dismissedDates;

    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
