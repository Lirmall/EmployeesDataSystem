package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.EmployeeDTO;
import ru.klokov.employeesdatasystem.dto.SalaryDTO;
import ru.klokov.employeesdatasystem.dto.SalarybyEmployeeOnPeriodDTO;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.SalaryEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.mappers.EmployeeEntityDTOMapper;
import ru.klokov.employeesdatasystem.mappers.SalaryEntityDTOMapper;
import ru.klokov.employeesdatasystem.mappers.WorktypeEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.SalaryService;

@RestController
@RequestMapping("/api/v1/admin/common/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final WorktypeEntityDTOMapper worktypeEntityDTOMapper;
    private final EmployeeEntityDTOMapper employeeEntityDTOMapper;
    private final SalaryEntityDTOMapper salaryEntityDTOMapper;
    private final SalaryService salaryService;

    @PostMapping("/averageSalaryByWorktype")
    public SalaryDTO getAverageSalaryByWorktype(@RequestBody WorktypeDTO worktypeDTO) {
        WorktypeEntity worktype = worktypeEntityDTOMapper.convertFromDTO(worktypeDTO);

        SalaryEntity averageSalaryByWorktype = salaryService.getAverageSalaryByWorktypeId(worktype);

        return salaryEntityDTOMapper.convertFromEntity(averageSalaryByWorktype);
    }

    @PostMapping("/monthSalaryByEmployee")
    public SalaryDTO getMonthSalaryByEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employee = employeeEntityDTOMapper.convertFromDTO(employeeDTO);

        SalaryEntity monthSalaryByEmployee = salaryService.getMonthSalaryByEmployeeId(employee);

        return salaryEntityDTOMapper.convertFromEntity(monthSalaryByEmployee);
    }

    @PostMapping("/salaryOnPeriodByEmployee")
    public SalaryDTO salaryOnPeriodByEmployee(@RequestBody SalarybyEmployeeOnPeriodDTO dto) {
        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        return salaryEntityDTOMapper.convertFromEntity(salaryEntity);
    }
}
