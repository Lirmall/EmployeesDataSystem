package ru.klokov.employeesdatasystem.specifications.positionsSpecification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PositionSpecification implements Specification<PositionEntity> {
    private PositionSearchModel request;

    @Override
    public Predicate toPredicate(Root<PositionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

        if (!CollectionUtils.isEmpty(request.getWorktypeId())) {
            Expression<Long> positionIdExpression = root.get("worktypeId");
            predicates.add(cb.and(positionIdExpression.in(request.getWorktypeId())));
        }

        if (!CollectionUtils.isEmpty(request.getSalaries())) {
            Expression<Long> positionIdExpression = root.get("salary");
            predicates.add(cb.and(positionIdExpression.in(request.getSalaries())));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
