package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.EmployeePositionRangeEntity;
import ru.klokov.employeesdatasystem.repositories.EmplPosRangeRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmplPosRangeService {
    private final EmplPosRangeRepository emplPosRangeRepository;

    @Transactional(readOnly = true)
    public Set<EmployeePositionRangeEntity> findActualEmployeeWithPosition(Long id) {
        return emplPosRangeRepository.findActualEmployeeWithPosition(id);
    }
}
