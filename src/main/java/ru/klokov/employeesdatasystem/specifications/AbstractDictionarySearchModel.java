package ru.klokov.employeesdatasystem.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractDictionarySearchModel {
    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
