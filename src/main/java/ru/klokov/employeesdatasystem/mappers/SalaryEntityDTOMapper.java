package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.DismissEmployeeDTO;
import ru.klokov.employeesdatasystem.dto.SalaryDTO;
import ru.klokov.employeesdatasystem.entities.DismissEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.SalaryEntity;

@Component
@RequiredArgsConstructor
public class SalaryEntityDTOMapper {
    private final ModelMapper modelMapper;

    public SalaryDTO convertFromEntity(SalaryEntity entity) {
        SalaryDTO dto = new SalaryDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public SalaryEntity convertFromDTO(SalaryDTO dto) {
        SalaryEntity entity = new SalaryEntity();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(dto, entity);
        return entity;
    }
}
