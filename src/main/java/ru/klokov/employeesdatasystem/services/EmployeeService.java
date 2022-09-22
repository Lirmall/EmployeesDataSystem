package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EmployeeEntity findById(Long id) {
        Optional<EmployeeEntity> foundRange = employeeRepository.findById(id);

        if (foundRange.isPresent()) {
            return foundRange.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Worktype wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public EmployeeEntity findGenderBySecondName(String name) {
        return employeeRepository.findEmployeeEntityBySecondName(name);
    }

}
