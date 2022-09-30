package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.CreateEmployeeDTO;
import ru.klokov.employeesdatasystem.dto.DismissEmployeeDTO;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.DismissEmployeeEntity;

@Component
@RequiredArgsConstructor
public class DismissEmployeeEntityDTOMapper {
    private final ModelMapper modelMapper;

    public DismissEmployeeDTO convertFromEntity(DismissEmployeeEntity entity) {
        DismissEmployeeDTO dto = new DismissEmployeeDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public DismissEmployeeEntity convertFromDTO(DismissEmployeeDTO dto) {
        DismissEmployeeEntity entity = new DismissEmployeeEntity();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(dto, entity);
        return entity;
    }
}
