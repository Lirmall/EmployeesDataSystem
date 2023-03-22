package ru.klokov.employeesdatasystem.security.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.dto.CreateSecurityUserDTO;
import ru.klokov.employeesdatasystem.security.dto.SecurityUserDTO;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;

@Component
@RequiredArgsConstructor
public class CreateSecurityUserEntityDTOMapper {
    private final ModelMapper modelMapper;

    public CreateSecurityUserDTO convertFromEntity(CreateSecurityUserEntity entity) {
        CreateSecurityUserDTO dto = new CreateSecurityUserDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public CreateSecurityUserEntity convertFromDTO(CreateSecurityUserDTO dto) {
        CreateSecurityUserEntity entity = new CreateSecurityUserEntity();
        modelMapper.map(dto, entity);
        return entity;
    }
}
