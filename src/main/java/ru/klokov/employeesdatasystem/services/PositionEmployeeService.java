package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;

@Service
@RequiredArgsConstructor
public class PositionEmployeeService {
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;


}
