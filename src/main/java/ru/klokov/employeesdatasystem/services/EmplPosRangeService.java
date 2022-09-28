package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.EmployeePositionRangeEntity;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.repositories.EmplPosRangeRepository;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmplPosRangeService {
    private final EmplPosRangeRepository emplPosRangeRepository;
    private final RangeService rangeService;

    @Transactional(readOnly = true)
    public Set<EmployeePositionRangeEntity> findActualEmployeeWithPosition(Long id) {
        return emplPosRangeRepository.findActualEmployeeWithPosition(id);
    }

    @Transactional
    public EmployeePositionRangeEntity addEmployeePositionRangeEntity(EmployeeEntity employee, PositionEntity position, RangeEntity range) {
        EmployeePositionRangeEntity epr = new EmployeePositionRangeEntity();
        epr.setEmployee(employee);
        epr.setPosition(position);

        if(position.getName().equals("Mechanic")) {
            epr.setRange(range);
        } else {
            epr.setRange(rangeService.findById(4L));
        }

        epr.setPositionChangeDate(LocalDate.now());

        emplPosRangeRepository.save(epr);

        return epr;
    }

    public long getCountOfTotalItems() {
        return emplPosRangeRepository.count();
    }
}
