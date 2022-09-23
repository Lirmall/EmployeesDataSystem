package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.PositionDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

@Component
@RequiredArgsConstructor
public class PositionEntityDTOMapper {
    private final ModelMapper modelMapper;

    public PositionDTO convertFromEntity(PositionEntity entity) {
        PositionDTO dto = new PositionDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public PositionEntity convertFromDTO(PositionDTO dto) {
        PositionEntity entity = new PositionEntity();
        modelMapper.map(dto, entity);
        return entity;
    }
}
