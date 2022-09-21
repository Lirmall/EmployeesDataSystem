package ru.klokov.employeesdatasystem.specifications.worktypesSpecification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class WorktypeSpecification implements Specification<WorktypeEntity> {
    private WorktypeSearchModel request;

    @Override
    public Predicate toPredicate(Root<WorktypeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getIds())) {
            Expression<Long> positionIdExpression = root.get("id");
            predicates.add(cb.and(positionIdExpression.in(request.getIds())));
        }

        if (!CollectionUtils.isEmpty(request.getNames())) {
            for(String search : request.getNames()) {
                predicates.add(cb.like(root.get("name"), "%" + search + "%"));
            }
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
