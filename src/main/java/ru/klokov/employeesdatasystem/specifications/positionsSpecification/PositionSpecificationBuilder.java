package ru.klokov.employeesdatasystem.specifications.positionsSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PositionSpecificationBuilder {

    private final List<PositionSearchCriteria> criteriaList;

    public PositionSpecificationBuilder() {
        criteriaList = new ArrayList<>();
    }

    public Specification build() {
        if(criteriaList.isEmpty()) {
            return null;
        }

        Specification result = new PositionSpecificationWithCriteria(criteriaList.get(0));

        for (int i = 1; i < criteriaList.size(); i++) {
            result = criteriaList.get(i).isOrPredicate()
                    ? Specification.where(result).or(new PositionSpecificationWithCriteria(criteriaList.get(i)))
                    : Specification.where(result).and(new PositionSpecificationWithCriteria(criteriaList.get(i)));
        }
        return result;
    }
}
