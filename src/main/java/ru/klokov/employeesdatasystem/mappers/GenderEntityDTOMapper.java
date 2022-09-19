package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.GenderDTO;
import ru.klokov.employeesdatasystem.entities.GenderEntity;

@Component
@RequiredArgsConstructor
public class GenderEntityDTOMapper  {
    private final ModelMapper modelMapper;

    public GenderDTO convertFromEntity(GenderEntity genderEntity) {
        GenderDTO genderDTO = new GenderDTO();
        modelMapper.map(genderEntity, genderDTO);
        return genderDTO;
    }

    public GenderEntity convertFromDTO(GenderDTO genderDTO) {
        GenderEntity genderEntity = new GenderEntity();
        modelMapper.map(genderDTO, genderEntity);
        return genderEntity;
    }
}
