package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.EmployeeDTO;
import ru.klokov.employeesdatasystem.dto.PositionDTO;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

@Component
@RequiredArgsConstructor
public class EmployeeEntityDTOMapper {
    private final ModelMapper modelMapper;

    public EmployeeDTO convertFromEntity(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public EmployeeEntity convertFromDTO(EmployeeDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        modelMapper.map(dto, entity);
        return entity;
    }
}
