package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.CreateEmployeeDTO;
import ru.klokov.employeesdatasystem.dto.UpdateEmployeeDTO;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.UpdateEmployeeEntity;

@Component
@RequiredArgsConstructor
public class UpdateEmployeeEntityDTOMapper {
    private final ModelMapper modelMapper;
    private final PositionEntityDTOMapper positionEntityDTOMapper;
    private final RangeEntityDTOMapper rangeEntityDTOMapper;

    public UpdateEmployeeDTO convertFromEntity(UpdateEmployeeEntity entity) {
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);

        dto.setPositionDTO(positionEntityDTOMapper.convertFromEntity(entity.getPosition()));
        dto.setRangeDTO(rangeEntityDTOMapper.convertFromEntity(entity.getRange()));

        return dto;
    }

    public UpdateEmployeeEntity convertFromDTO(UpdateEmployeeDTO dto) {
        UpdateEmployeeEntity entity = new UpdateEmployeeEntity();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(dto, entity);

        entity.setPosition(positionEntityDTOMapper.convertFromDTO(dto.getPositionDTO()));
        entity.setRange(rangeEntityDTOMapper.convertFromDTO(dto.getRangeDTO()));

        return entity;
    }
}
