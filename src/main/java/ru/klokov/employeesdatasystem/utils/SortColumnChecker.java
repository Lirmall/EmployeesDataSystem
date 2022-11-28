package ru.klokov.employeesdatasystem.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.specifications.AbstractDictionarySearchModel;
import ru.klokov.employeesdatasystem.specifications.employeeSpecification.worktypesSpecification.EmployeeSearchModel;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GendersSearchModel;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchCriteria;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchModel;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSearchModel;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;

@Component
public class SortColumnChecker {
    public Sort sortColumnCheck(GendersSearchModel request) {
        return dictionarySortColumnChecker(request);
    }

    public Sort sortColumnCheck(WorktypeSearchModel request) {
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

    public Sort sortColumnCheck(PositionSearchModel request) {
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
            String column;
            switch (substring) {
                case "name":
                    column = "name";
                    break;
                case "worktypeid":
                    column = "worktypeId";
                    break;
                case "salaries":
                    column = "salaries";
                    break;
                default:
                    column = "id";
            }
            sort = Sort.by(sortDirection, column);
        } else {
            sort = Sort.by(sortDirection, "id");
        }

        return sort;
    }

//    public Sort sortColumnCheck(PositionSearchCriteria request) {
//        Sort.Direction sortDirection = Sort.Direction.ASC;
//        String sortColumn = request.getSortColumn();
//        String substring;
//        Sort sort;
//
//        if (sortColumn != null) {
//            if (sortColumn.startsWith("-")) {
//                sortDirection = Sort.Direction.DESC;
//                if (sortColumn.length() == 1) {
//                    substring = "id";
//                } else {
//                    substring = sortColumn.substring(1).toLowerCase();
//                }
//            } else {
//                substring = sortColumn.toLowerCase();
//            }
//            String column;
//            switch (substring) {
//                case "name":
//                    column = "name";
//                    break;
//                case "worktypeid":
//                    column = "worktypeId";
//                    break;
//                case "salaries":
//                    column = "salaries";
//                    break;
//                default:
//                    column = "id";
//            }
//            sort = Sort.by(sortDirection, column);
//        } else {
//            sort = Sort.by(sortDirection, "id");
//        }
//
//        return sort;
//    }

    public Sort sortColumnCheck(String request) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String substring;
        Sort sort;

        if (request != null) {
            if (request.startsWith("-")) {
                sortDirection = Sort.Direction.DESC;
                if (request.length() == 1) {
                    substring = "id";
                } else {
                    substring = request.substring(1).toLowerCase();
                }
            } else {
                substring = request.toLowerCase();
            }
            String column;
            switch (substring) {
                case "name":
                    column = "name";
                    break;
                case "worktypeid":
                    column = "worktypeId";
                    break;
                case "salaries":
                    column = "salaries";
                    break;
                default:
                    column = "id";
            }
            sort = Sort.by(sortDirection, column);
        } else {
            sort = Sort.by(sortDirection, "id");
        }

        return sort;
    }

    public Sort sortColumnCheck(RangeSearchModel request) {
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
            String column;
            switch (substring) {
                case "names":
                    column = "names";
                    break;
                case "bonuses":
                    column = "bonuses";
                    break;
                default:
                    column = "id";
            }
            sort = Sort.by(sortDirection, column);
        } else {
            sort = Sort.by(sortDirection, "id");
        }

        return sort;
    }

    public Sort sortColumnCheck(EmployeeSearchModel request) {
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
            String column;
            switch (substring) {
                case "names":
                    column = "names";
                    break;
                case "secondnames":
                    column = "secondNames";
                    break;
                case "firstnames":
                    column = "firstNames";
                    break;
                case "thirdnames":
                    column = "thirdNames";
                    break;
                case "genderids":
                    column = "genderIds";
                    break;
                case "birthdatdates":
                    column = "birthdayDates";
                    break;
                case "worktypeids":
                    column = "worktypeIds";
                    break;
                case "salaries":
                    column = "salaries";
                    break;
                case "workstardates":
                    column = "workstartDates";
                    break;
                case "dismissed":
                    column = "dismissed";
                    break;
                case "dismisseddates":
                    column = "dismissedDates";
                    break;
                default:
                    column = "id";
            }
            sort = Sort.by(sortDirection, column);
        } else {
            sort = Sort.by(sortDirection, "id");
        }

        return sort;
    }
}
