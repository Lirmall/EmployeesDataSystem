package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;

@Service
@RequiredArgsConstructor
public class PositionEmployeeService {
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;


    @Transactional(readOnly = true)
    public PositionEntity findPositionByName(String name) {
        return positionRepository.findPositionEntityByName(name);
    }
}
