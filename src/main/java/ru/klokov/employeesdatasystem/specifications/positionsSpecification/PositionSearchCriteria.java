package ru.klokov.employeesdatasystem.specifications.positionsSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionSearchCriteria {
    private String fieldName;
    private String operation;
    private Object fieldValue;

    private boolean orPredicate = false;
//
//    Integer pages = 0;
//
//    Integer limit = 5;
//
//    String sortColumn;
}
