package ru.klokov.employeesdatasystem.specifications.positionsSpecification;

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
public class PositionSearchModel extends AbstractDictionarySearchModel {
    List<Long> ids;
    List<String> names;
    List<Long> worktypeId;
    List<Double> salaries;

    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
