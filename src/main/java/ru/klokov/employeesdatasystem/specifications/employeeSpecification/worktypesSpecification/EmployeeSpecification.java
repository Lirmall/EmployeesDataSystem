package ru.klokov.employeesdatasystem.specifications.employeeSpecification.worktypesSpecification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class EmployeeSpecification implements Specification<EmployeeEntity> {
    private EmployeeSearchModel request;

    @Override
    public Predicate toPredicate(Root<EmployeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getIds())) {
            Expression<Long> employeeIdExpression = root.get("id");
            predicates.add(cb.and(employeeIdExpression.in(request.getIds())));
        }

        if (!CollectionUtils.isEmpty(request.getSecondNames())) {
            for(String search : request.getSecondNames()) {
                predicates.add(cb.like(root.get("secondName"), "%" + search + "%"));
            }
        }

        if (!CollectionUtils.isEmpty(request.getFirstNames())) {
            for(String search : request.getFirstNames()) {
                predicates.add(cb.like(root.get("firstName"), "%" + search + "%"));
            }
        }

        if (!CollectionUtils.isEmpty(request.getThirdNames())) {
            for(String search : request.getThirdNames()) {
                predicates.add(cb.like(root.get("thirdName"), "%" + search + "%"));
            }
        }

        if (!CollectionUtils.isEmpty(request.getGenderIds())) {
            Expression<Long> employeeIdExpression = root.get("genderId");
            predicates.add(cb.and(employeeIdExpression.in(request.getGenderIds())));
        }

        if (!CollectionUtils.isEmpty(request.getBirthdayDates())) {
            for(String search : request.getBirthdayDates()) {
                predicates.add(cb.like(root.get("birthdayDates"), "%" + search + "%"));
            }
        }

        if (!CollectionUtils.isEmpty(request.getWorktypeIds())) {
            Expression<Long> employeeIdExpression = root.get("worktypeId");
            predicates.add(cb.and(employeeIdExpression.in(request.getWorktypeIds())));
        }

        if (!CollectionUtils.isEmpty(request.getSalaries())) {
            Expression<Long> employeeIdExpression = root.get("salary");
            predicates.add(cb.and(employeeIdExpression.in(request.getSalaries())));
        }

        if (!CollectionUtils.isEmpty(request.getWorkstartDates())) {
            for(String search : request.getWorkstartDates()) {
                predicates.add(cb.like(root.get("workstartDates"), "%" + search + "%"));
            }
        }

        if (!CollectionUtils.isEmpty(request.getDismissed())) {
            Expression<Long> employeeIdExpression = root.get("dismissed");
            predicates.add(cb.and(employeeIdExpression.in(request.getDismissed())));
        }

        if (!CollectionUtils.isEmpty(request.getDismissedDates())) {
            for(String search : request.getDismissedDates()) {
                predicates.add(cb.like(root.get("dismissedDates"), "%" + search + "%"));
            }
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
