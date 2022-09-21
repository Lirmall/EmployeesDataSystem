package ru.klokov.employeesdatasystem.specifications.rangesSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.klokov.employeesdatasystem.specifications.AbstractDictionarySearchModel;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RangeSearchModel extends AbstractDictionarySearchModel {
    List<Long> ids;
    List<String> names;
    List<Double> bonuses;

    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
