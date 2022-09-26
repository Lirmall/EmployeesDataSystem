package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.PositionDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.PositionEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.PositionService;
import ru.klokov.employeesdatasystem.specifications.Response;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/common/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionEntityDTOMapper positionEntityDTOMapper;
    private final PositionService positionService;

    @GetMapping
    public List<PositionDTO> findAll() {
        List<PositionEntity> allPositions = positionService.findAll();
        List<PositionDTO> result = new ArrayList<>();

        for (PositionEntity position : allPositions) {
            result.add(positionEntityDTOMapper.convertFromEntity(position));
        }
        return result;
    }

    @GetMapping("/{id}")
    public PositionDTO getById(@PathVariable("id") Long id) {
        PositionEntity positionEntity;

        try {
            positionEntity = positionService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find gender with id = " + id);
        }

        return positionEntityDTOMapper.convertFromEntity(positionEntity);
    }

    @PostMapping("/filter")
    public Response<PositionDTO> getPositions(@RequestBody PositionSearchModel request) {
        Long countOfTotalElements = positionService.getCountOfTotalItems();
        Page<PositionEntity> genders = positionService.findByFilter(request);

        if (genders.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<PositionDTO> positionDTOS = genders.map(positionEntityDTOMapper::convertFromEntity);
            return new Response<>(positionDTOS.toList(), countOfTotalElements, positionDTOS.getTotalElements());
        }
    }

    @PostMapping
    public PositionDTO add(@RequestBody PositionDTO positionDTO) {
        positionDTO.setId(null);

        PositionEntity positionEntity = positionEntityDTOMapper.convertFromDTO(positionDTO);

        WorktypeEntity worktype = positionService.worktypeCheck(positionDTO.getWorktype());

        positionEntity.setWorktype(worktype);

        PositionEntity createdPosition =positionService.create(positionEntity);

        return positionEntityDTOMapper.convertFromEntity(createdPosition);
    }

    @PutMapping("/{name}")
    public PositionDTO updateByName(@RequestBody PositionDTO positionDTO) {
        String name = positionDTO.getName();

        PositionEntity positionToUpdate;

        if(name != null) {
            positionToUpdate = positionService.findPositionByName(name);
        } else {
            throw new NoMatchingEntryInDatabaseException("Unable to find position with empty or null name");
        }

        PositionEntity updatePositionData = positionEntityDTOMapper.convertFromDTO(positionDTO);

        if(positionToUpdate != null) {
            PositionEntity updatedPosition = positionService.putUpdate(positionService.findPositionByName(name), updatePositionData);
            return positionEntityDTOMapper.convertFromEntity(updatedPosition);
        } else {
            throw new NoMatchingEntryInDatabaseException("Position with name \"" + name + "\" not found");
        }
    }

        @DeleteMapping("/{id}")
    public void deletePositionById(@PathVariable("id") Long id) {
        positionService.deleteById(id);
    }
}
