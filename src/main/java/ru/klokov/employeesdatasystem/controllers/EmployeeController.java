package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.CreateEmployeeDTO;
import ru.klokov.employeesdatasystem.dto.DismissEmployeeDTO;
import ru.klokov.employeesdatasystem.dto.EmployeeDTO;
import ru.klokov.employeesdatasystem.dto.UpdateEmployeeDTO;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.DismissEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.UpdateEmployeeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.CreateEmployeeEntityDTOMapper;
import ru.klokov.employeesdatasystem.mappers.DismissEmployeeEntityDTOMapper;
import ru.klokov.employeesdatasystem.mappers.EmployeeEntityDTOMapper;
import ru.klokov.employeesdatasystem.mappers.UpdateEmployeeEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.EmployeeService;
import ru.klokov.employeesdatasystem.specifications.Response;
import ru.klokov.employeesdatasystem.specifications.employeeSpecification.EmployeeSearchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/common/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeEntityDTOMapper employeeEntityDTOMapper;
    private final EmployeeService employeeService;
    private final CreateEmployeeEntityDTOMapper createEmployeeEntityDTOMapper;
    private final DismissEmployeeEntityDTOMapper dismissEmployeeEntityDTOMapper;
    private final UpdateEmployeeEntityDTOMapper updateEmployeeEntityDTOMapper;

    @PostMapping
    @PreAuthorize("hasPermission('employee', 'create')")
    public EmployeeDTO add(@RequestBody CreateEmployeeDTO createEmployeeDTO) {
        CreateEmployeeEntity createEmployeeEntity = createEmployeeEntityDTOMapper.convertFromDTO(createEmployeeDTO);

        EmployeeEntity createdEmployee = employeeService.create(createEmployeeEntity);

        return employeeEntityDTOMapper.convertFromEntity(createdEmployee);
    }

    @GetMapping
    @PreAuthorize("hasPermission('employee', 'read')")
    public List<EmployeeDTO> findAll() {
        List<EmployeeEntity> allEmployees = employeeService.findAll();
        List<EmployeeDTO> result = new ArrayList<>();

        for (EmployeeEntity employee : allEmployees) {
            result.add(employeeEntityDTOMapper.convertFromEntity(employee));
        }
        return result;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('employee', 'readById')")
    public EmployeeDTO getById(@PathVariable("id") Long id) {
        EmployeeEntity employeeEntity;

        try {
            employeeEntity = employeeService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find employee with id = " + id);
        }

        return employeeEntityDTOMapper.convertFromEntity(employeeEntity);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasPermission('employee', 'readByFilter')")
    public Response<EmployeeDTO> getEmployeesByFilter(@RequestBody EmployeeSearchModel request) {
        Long countOfTotalElements = employeeService.getCountOfTotalItems();
        Page<EmployeeEntity> genders = employeeService.findByFilter(request);

        if(genders.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<EmployeeDTO> rangeDTOS = genders.map(employeeEntityDTOMapper::convertFromEntity);
            return new Response<>(rangeDTOS.toList(), countOfTotalElements, rangeDTOS.getTotalElements());
        }
    }


    @PostMapping("/dismiss")
    @PreAuthorize("hasPermission('employee', 'dismiss')")
    public EmployeeDTO dismissEmployee(@RequestBody DismissEmployeeDTO dismissEmployeeDTO) {
        DismissEmployeeEntity dismissEmployeeEntity = dismissEmployeeEntityDTOMapper.convertFromDTO(dismissEmployeeDTO);

        EmployeeEntity dismissedEmployee = employeeService.dismissEmployee(dismissEmployeeEntity);

        return employeeEntityDTOMapper.convertFromEntity(dismissedEmployee);
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission('employee', 'update')")
    public EmployeeDTO updateEmployee(@RequestBody UpdateEmployeeDTO updateEmployeeDTO) {
        UpdateEmployeeEntity updateEmployeeEntity = updateEmployeeEntityDTOMapper.convertFromDTO(updateEmployeeDTO);

        EmployeeEntity dismissedEmployee = employeeService.updateEmployee(updateEmployeeEntity);

        return employeeEntityDTOMapper.convertFromEntity(dismissedEmployee);
    }
}
