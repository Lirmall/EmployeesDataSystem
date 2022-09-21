package ru.klokov.employeesdatasystem.specifications.worktypesSpecification;

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
public class WorktypeSearchModel extends AbstractDictionarySearchModel {
    List<Long> ids;
    List<String> names;

    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
