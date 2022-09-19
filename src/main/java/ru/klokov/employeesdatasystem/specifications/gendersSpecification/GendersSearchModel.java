package ru.klokov.employeesdatasystem.specifications.gendersSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class GendersSearchModel {
    List<Long> ids;
    List<String> names;

    Integer pages = 0;

    Integer limit = 5;

    String sortColumn;
}
