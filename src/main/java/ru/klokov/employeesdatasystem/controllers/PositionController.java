package ru.klokov.employeesdatasystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.dto.AddPositionDTO;
import ru.klokov.employeesdatasystem.dto.PositionDTO;
import ru.klokov.employeesdatasystem.dto.PositionsSearchSpecificationsDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.mappers.PositionEntityDTOMapper;
import ru.klokov.employeesdatasystem.services.PositionService;
import ru.klokov.employeesdatasystem.specifications.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/common/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionEntityDTOMapper positionEntityDTOMapper;
    private final PositionService positionService;

    @GetMapping
    @PreAuthorize("hasPermission('positions', 'read')")
    public List<PositionDTO> findAll() {
        List<PositionEntity> allPositions = positionService.findAll();
        List<PositionDTO> result = new ArrayList<>();

        for (PositionEntity position : allPositions) {
            result.add(positionEntityDTOMapper.convertFromEntity(position));
        }
        return result;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('positions', 'readById')")
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
//
//    @PostMapping("/filter")
//    public Response<PositionDTO> getPositions(@RequestBody PositionSearchModel request) {
//        Long countOfTotalElements = positionService.getCountOfTotalItems();
//        Page<PositionEntity> positions = positionService.findByFilter(request);
//
//        if (positions.isEmpty()) {
//            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
//        } else {
//            Page<PositionDTO> positionDTOS = positions.map(positionEntityDTOMapper::convertFromEntity);
//            return new Response<>(positionDTOS.toList(), countOfTotalElements, positionDTOS.getTotalElements());
//        }
//    }

    @PostMapping("/filter")
    @PreAuthorize("hasPermission('positions', 'readByFilter')")
    public Response<PositionDTO> getPositionsByFilter(@RequestBody PositionsSearchSpecificationsDTO request) {
        Long countOfTotalElements = positionService.getCountOfTotalItems();
        Page<PositionEntity> entities = positionService.findByFilterWithNewCriteriaAndSpecification(request);

        if (entities.isEmpty()) {
            return new Response<>(Collections.emptyList(), countOfTotalElements, 0L);
        } else {
            Page<PositionDTO> positionDTOS = entities.map(positionEntityDTOMapper::convertFromEntity);
            return new Response<>(positionDTOS.toList(), countOfTotalElements, positionDTOS.getTotalElements());
        }
    }
//
//    @PostMapping("/new")
//    public PositionDTO add(@RequestBody PositionDTO positionDTO) {
//        positionDTO.setId(null);
//
//        PositionEntity positionEntity = positionEntityDTOMapper.convertFromDTO(positionDTO);
//
//        WorktypeEntity worktype = positionService.worktypeCheck(positionDTO.getWorktype());
//
//        positionEntity.setWorktype(worktype);
//
//        PositionEntity createdPosition = positionService.create(positionEntity);
//
//        return positionEntityDTOMapper.convertFromEntity(createdPosition);
//    }

    @PostMapping("/new")
    @PreAuthorize("hasPermission('positions', 'create')")
    public PositionDTO add(@RequestBody AddPositionDTO addPositionDTO) {

        PositionEntity positionEntity = new PositionEntity();

        WorktypeEntity worktype = positionService.worktypeCheck(addPositionDTO.getWorktype());

        positionEntity.setName(addPositionDTO.getName());
        positionEntity.setSalary(addPositionDTO.getSalary());
        positionEntity.setWorktype(worktype);

        PositionEntity createdPosition = positionService.create(positionEntity);

        return positionEntityDTOMapper.convertFromEntity(createdPosition);
    }

//    @PutMapping("/{name}")
//    public PositionDTO updateByName(@RequestBody PositionDTO positionDTO) {
//        String name = positionDTO.getName();
//
//        PositionEntity positionToUpdate;
//
//        if(name != null) {
//            positionToUpdate = positionService.findPositionByName(name);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Unable to find position with empty or null name");
//        }
//
//        PositionEntity updatePositionData = positionEntityDTOMapper.convertFromDTO(positionDTO);
//
//        if(positionToUpdate != null) {
//            PositionEntity updatedPosition = positionService.putUpdate(positionService.findPositionByName(name), updatePositionData);
//            return positionEntityDTOMapper.convertFromEntity(updatedPosition);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Position with name \"" + name + "\" not found");
//        }
//    }

//    @PutMapping("/put/{id}")
//    public PositionDTO putUpdateById(@RequestBody PositionDTO positionDTO) {
//        String name = positionDTO.getName();
//        Long id = positionDTO.getId();
//
//        PositionEntity positionToUpdate;
//
//        if (name != null) {
//            positionToUpdate = positionService.findById(id);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Unable to find position with empty or null id");
//        }
//
//        PositionEntity updatePositionData = positionEntityDTOMapper.convertFromDTO(positionDTO);
//
//        if (positionToUpdate != null) {
//            PositionEntity updatedPosition = positionService.putUpdate(positionService.findById(id), updatePositionData);
//            return positionEntityDTOMapper.convertFromEntity(updatedPosition);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Position with id \"" + id + "\" not found");
//        }
//    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasPermission('positions', 'update')")
    public PositionDTO patchUpdateById(@RequestBody PositionDTO positionDTO) {
        String name = positionDTO.getName();
        Long id = positionDTO.getId();

        PositionEntity positionToUpdate;

        if (name != null) {
            positionToUpdate = positionService.findById(id);
        } else {
            throw new NoMatchingEntryInDatabaseException("Unable to find position with empty or null id");
        }

        PositionEntity updatePositionData = positionEntityDTOMapper.convertFromDTO(positionDTO);

        if (positionToUpdate != null) {
            PositionEntity updatedPosition = positionService.patchUpdate(positionService.findById(id), updatePositionData);
            return positionEntityDTOMapper.convertFromEntity(updatedPosition);
        } else {
            throw new NoMatchingEntryInDatabaseException("Position with id \"" + id + "\" not found");
        }
    }

//    @PatchMapping("/{id}")
//    public PositionDTO patchUpdateById(@RequestBody AddPositionDTO addPositionDTO) {
//        String name = addPositionDTO.getName();
//        Long id = addPositionDTO.getId();
//
//        PositionEntity positionToUpdate;
//
//        if (name != null) {
//            positionToUpdate = positionService.findById(id);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Unable to find position with empty or null id");
//        }
//
//        WorktypeEntity worktype = positionService.worktypeCheck(addPositionDTO.getWorktype());
//
//        PositionEntity updatePositionData = new PositionEntity();
//        updatePositionData.setName(addPositionDTO.getName());
//        updatePositionData.setSalary(addPositionDTO.getSalary());
//        updatePositionData.setWorktype(worktype);
//
//        if (positionToUpdate != null) {
//            PositionEntity updatedPosition = positionService.patchUpdate(positionService.findById(id), updatePositionData);
//            return positionEntityDTOMapper.convertFromEntity(updatedPosition);
//        } else {
//            throw new NoMatchingEntryInDatabaseException("Position with id \"" + id + "\" not found");
//        }
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission('positions', 'deleteById')")
    public void deletePositionById(@PathVariable("id") Long id) {
        positionService.deleteById(id);
    }
}
