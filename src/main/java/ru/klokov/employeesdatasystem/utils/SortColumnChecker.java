package ru.klokov.employeesdatasystem.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.specifications.AbstractDictionarySearchModel;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GendersSearchModel;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.gendersSpecification.WorktypeSearchModel;

@Component
public class SortColumnChecker {
    public Sort genderSortColumnCheck(GendersSearchModel request) {
        return dictionarySortColumnChecker(request);
    }

    public Sort worktypeSortColumnCheck(WorktypeSearchModel request) {
        return dictionarySortColumnChecker(request);
    }

    private Sort dictionarySortColumnChecker(AbstractDictionarySearchModel request) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String sortColumn = request.getSortColumn();
        String substring;
        Sort sort;

        if (sortColumn != null) {
            if (sortColumn.startsWith("-")) {
                sortDirection = Sort.Direction.DESC;
                if (sortColumn.length() == 1) {
                    substring = "id";
                } else {
                    substring = sortColumn.substring(1).toLowerCase();
                }
            } else {
                substring = sortColumn.toLowerCase();
            }

            if (substring.equalsIgnoreCase("id") || substring.equalsIgnoreCase("name")) {
                sort = Sort.by(sortDirection, substring);
            } else {
                sort = Sort.by(sortDirection, "id");
            }

        } else {
            sort = Sort.by(sortDirection, "id");
        }

        return sort;
    }
}
