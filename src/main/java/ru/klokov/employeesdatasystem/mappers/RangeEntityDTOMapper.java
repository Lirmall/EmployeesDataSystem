package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.RangeDTO;
import ru.klokov.employeesdatasystem.entities.RangeEntity;

@Component
@RequiredArgsConstructor
public class RangeEntityDTOMapper {
    private final ModelMapper modelMapper;

    public RangeDTO convertFromEntity(RangeEntity entity) {
        RangeDTO dto = new RangeDTO();
        modelMapper.map(entity, dto);
        return dto;
    }

    public RangeEntity convertFromDTO(RangeDTO dto) {
        RangeEntity entity = new RangeEntity();
        modelMapper.map(dto, entity);
        return entity;
    }
}
