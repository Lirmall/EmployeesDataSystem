package ru.klokov.employeesdatasystem.security.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.EmployeeDTO;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.dto.SecurityUserDTO;

@Component
@RequiredArgsConstructor
public class SecurityUserEntityDTOMapper {
    private final ModelMapper modelMapper;

    public SecurityUserDTO convertFromEntity(SecurityUser entity) {
        SecurityUserDTO dto = new SecurityUserDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(entity, dto);
        return dto;
    }

    public SecurityUser convertFromDTO(SecurityUserDTO dto) {
        SecurityUser entity = new SecurityUser();
        modelMapper.map(dto, entity);
        return entity;
    }
}
