package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.GenderDTO;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.GenderEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.GenderService;
import ru.klokov.employeesdatasystem.specifications.Response;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GendersSearchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/common/genders")
@RequiredArgsConstructor
public class GendersController {

    private final GenderEntityDTOMapper genderEntityDTOMapper;
    private final GenderService genderService;

    @GetMapping
    @PreAuthorize("hasPermission('genders', 'read')")
    public List<GenderDTO> findAll() {
        List<GenderEntity> allGenders = genderService.findAll();
        List<GenderDTO> result = new ArrayList<>();

        for (GenderEntity genders : allGenders) {
            result.add(genderEntityDTOMapper.convertFromEntity(genders));
        }
        return result;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('genders', 'readById')")
    public GenderDTO getById(@PathVariable("id") Long id) {
        GenderEntity genderEntity;

        try {
            genderEntity = genderService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find gender with id = " + id);
        }

        return genderEntityDTOMapper.convertFromEntity(genderEntity);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasPermission('genders', 'readByFilter')")
    public Response<GenderDTO> getGenders(@RequestBody GendersSearchModel request) {
        Long countOfTotalElements = genderService.getCountOfTotalItems();
        Page<GenderEntity> genders = genderService.findByFilter(request);

        if(genders.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<GenderDTO> genderDTOS = genders.map(genderEntityDTOMapper::convertFromEntity);
            return new Response<>(genderDTOS.toList(), countOfTotalElements, genderDTOS.getTotalElements());
        }
    }
}
