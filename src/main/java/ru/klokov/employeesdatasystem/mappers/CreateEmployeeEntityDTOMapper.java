package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.CreateEmployeeDTO;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntity;

@Component
@RequiredArgsConstructor
public class CreateEmployeeEntityDTOMapper {
    private final ModelMapper modelMapper;
    private final PositionEntityDTOMapper positionEntityDTOMapper;
    private final RangeEntityDTOMapper rangeEntityDTOMapper;
    private final GenderEntityDTOMapper genderEntityDTOMapper;

    public CreateEmployeeDTO convertFromEntity(CreateEmployeeEntity entity) {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);

        dto.setPositionDTO(positionEntityDTOMapper.convertFromEntity(entity.getPosition()));
        dto.setGenderDTO(genderEntityDTOMapper.convertFromEntity(entity.getGender()));
        dto.setRangeDTO(rangeEntityDTOMapper.convertFromEntity(entity.getRange()));

        return dto;
    }

    public CreateEmployeeEntity convertFromDTO(CreateEmployeeDTO dto) {
        CreateEmployeeEntity entity = new CreateEmployeeEntity();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(dto, entity);

        entity.setPosition(positionEntityDTOMapper.convertFromDTO(dto.getPositionDTO()));
        entity.setGender(genderEntityDTOMapper.convertFromDTO(dto.getGenderDTO()));
        entity.setRange(rangeEntityDTOMapper.convertFromDTO(dto.getRangeDTO()));

        return entity;
    }
}
