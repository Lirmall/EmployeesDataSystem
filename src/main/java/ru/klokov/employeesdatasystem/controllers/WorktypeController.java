package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.WorktypeEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.WorktypeService;
import ru.klokov.employeesdatasystem.specifications.Response;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/common/worktypes")
@RequiredArgsConstructor
public class WorktypeController {

    private final WorktypeEntityDTOMapper worktypeEntityDTOMapper;
    private final WorktypeService worktypeService;

    @GetMapping
    public List<WorktypeDTO> findAll() {
        List<WorktypeEntity> allGenders = worktypeService.findAll();
        List<WorktypeDTO> result = new ArrayList<>();

        for (WorktypeEntity genders : allGenders) {
            result.add(worktypeEntityDTOMapper.convertFromEntity(genders));
        }
        return result;
    }

    @GetMapping("/{id}")
    public WorktypeDTO getById(@PathVariable("id") Long id) {
        WorktypeEntity genderEntity;

        try {
            genderEntity = worktypeService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find gender with id = " + id);
        }

        return worktypeEntityDTOMapper.convertFromEntity(genderEntity);
    }

    @PostMapping("/filter")
    public Response<WorktypeDTO> getWorktypesByFilter(@RequestBody WorktypeSearchModel request) {
        Long countOfTotalElements = worktypeService.getCountOfTotalItems();
        Page<WorktypeEntity> genders = worktypeService.findByFilter(request);

        if(genders.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<WorktypeDTO> worktypeDTOS = genders.map(worktypeEntityDTOMapper::convertFromEntity);
            return new Response<>(worktypeDTOS.toList(), countOfTotalElements, worktypeDTOS.getTotalElements());
        }
    }
}
