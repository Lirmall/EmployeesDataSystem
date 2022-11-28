package ru.klokov.employeesdatasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import ru.klokov.employeesdatasystem.specifications.pageSettings.PageSettings;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchCriteria;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSpecificationWithCriteria;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionsSearchSpecificationsDTO {

    private List<PositionSearchCriteria> specifications = new ArrayList<>();
    private PageSettings pageSettings;
}
