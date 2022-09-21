package ru.klokov.employeesdatasystem.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.klokov.employeesdatasystem.dto.GenderDTO;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

@Component
@RequiredArgsConstructor
public class WorktypeEntityDTOMapper {
    private final ModelMapper modelMapper;

    public WorktypeDTO convertFromEntity(WorktypeEntity worktypeEntity) {
        WorktypeDTO worktypeDTO = new WorktypeDTO();
        modelMapper.map(worktypeEntity, worktypeDTO);
        return worktypeDTO;
    }

    public WorktypeEntity convertFromDTO(WorktypeDTO worktypeDTO) {
        WorktypeEntity worktypeEntity = new WorktypeEntity();
        modelMapper.map(worktypeDTO, worktypeEntity);
        return worktypeEntity;
    }
}
