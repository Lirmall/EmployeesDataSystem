package ru.klokov.employeesdatasystem.specifications.positionsSpecification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

import javax.persistence.criteria.*;

@AllArgsConstructor
public class PositionSpecificationWithCriteria implements Specification<PositionEntity> {
    private PositionSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<PositionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return cb.greaterThanOrEqualTo(
                    root.<String> get(criteria.getFieldName()), criteria.getFieldValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return cb.lessThanOrEqualTo(
                    root.<String> get(criteria.getFieldName()), criteria.getFieldValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getFieldName()).getJavaType() == String.class) {
                return cb.like(
                        root.<String>get(criteria.getFieldName()), "%" + criteria.getFieldValue() + "%");
            } else {
                return cb.equal(root.get(criteria.getFieldName()), criteria.getFieldValue());
            }
        }
        return null;
    }
}
