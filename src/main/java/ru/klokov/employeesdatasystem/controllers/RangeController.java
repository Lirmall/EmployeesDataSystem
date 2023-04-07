package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.RangeDTO;
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.RangeEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.RangeService;
import ru.klokov.employeesdatasystem.specifications.Response;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSearchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/common/ranges")
@RequiredArgsConstructor
public class RangeController {

    private final RangeEntityDTOMapper rangeEntityDTOMapper;
    private final RangeService rangeService;

    @GetMapping
    @PreAuthorize("hasPermission('ranges', 'read')")
    public List<RangeDTO> findAll() {
        List<RangeEntity> allGenders = rangeService.findAll();
        List<RangeDTO> result = new ArrayList<>();

        for (RangeEntity genders : allGenders) {
            result.add(rangeEntityDTOMapper.convertFromEntity(genders));
        }
        return result;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('ranges', 'readById')")
    public RangeDTO getById(@PathVariable("id") Long id) {
        RangeEntity rangeEntity;

        try {
            rangeEntity = rangeService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find range with id = " + id);
        }

        return rangeEntityDTOMapper.convertFromEntity(rangeEntity);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasPermission('ranges', 'readByFilter')")
    public Response<RangeDTO> getRangesByFilter(@RequestBody RangeSearchModel request) {
        Long countOfTotalElements = rangeService.getCountOfTotalItems();
        Page<RangeEntity> genders = rangeService.findByFilter(request);

        if(genders.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<RangeDTO> rangeDTOS = genders.map(rangeEntityDTOMapper::convertFromEntity);
            return new Response<>(rangeDTOS.toList(), countOfTotalElements, rangeDTOS.getTotalElements());
        }
    }
}
