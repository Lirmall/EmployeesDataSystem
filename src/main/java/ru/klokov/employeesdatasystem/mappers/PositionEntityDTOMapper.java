package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.PositionDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

@Component
@RequiredArgsConstructor
public class PositionEntityDTOMapper {
    private final ModelMapper modelMapper;

    public PositionDTO convertFromEntity(PositionEntity entity) {
        PositionDTO dto = new PositionDTO();
        modelMapper.map(entity, dto);
        return dto;
    }

    public PositionEntity convertFromDTO(PositionDTO dto) {
        PositionEntity entity = new PositionEntity();
        modelMapper.map(dto, entity);
        return entity;
    }
}
