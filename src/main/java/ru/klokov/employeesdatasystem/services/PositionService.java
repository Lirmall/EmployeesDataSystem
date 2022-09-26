package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.EmployeePositionRangeEntity;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.AlreadyCreatedException;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.exceptions.PositionHasEmployeesException;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchModel;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSpecification;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final SortColumnChecker sortColumnChecker;
    private final PositionWorktypeService positionWorktypeService;
    private final EmplPosRangeService emplPosRangeService;

    @Transactional
    public PositionEntity create(PositionEntity positionToCreate) {
        if (findPositionByName(positionToCreate.getName()) == null) {
            positionToCreate.setId(null);
            positionRepository.save(positionToCreate);
            return positionRepository.findPositionEntityByName(positionToCreate.getName());
        } else {
            throw new AlreadyCreatedException("Position wit name \"" + positionToCreate.getName() + "\" is already created");
        }
    }

    @Transactional(readOnly = true)
    public List<PositionEntity> findAll() {
        return positionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PositionEntity findById(Long id) {
        Optional<PositionEntity> foundPosition;

        if (id == null) {
            throw new NullOrEmptyArgumentexception("Id can't be null");
        } else {
            foundPosition = positionRepository.findById(id);
        }

        if (foundPosition.isPresent()) {
            return foundPosition.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Position wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public PositionEntity findPositionByName(String name) {
        return positionRepository.findPositionEntityByName(name);
    }

    @Transactional(readOnly = true)
    public Page<PositionEntity> findByFilter(PositionSearchModel request) {
        Sort sort = sortColumnChecker.sortColumnCheck(request);

        int page = request.getPages() != null ? request.getPages() : 0;
        int size = (request.getLimit() != null && request.getLimit() != 0) ? request.getLimit() : 5;

        Pageable pageable = PageRequest.of(page, size, sort);

        if (request.getIds().isEmpty() && request.getNames().isEmpty()) {
            return Page.empty();
        } else {
            return positionRepository.findAll(new PositionSpecification(request), pageable);
        }
    }

    @Transactional
    public PositionEntity putUpdate(PositionEntity positionToUpdate, PositionEntity updateData) {
        PositionEntity positionEntity;
        updateData.setId(null);

        PositionSearchModel positionSearchModel = new PositionSearchModel();

        positionSearchModel.setIds(Collections.singletonList(positionToUpdate.getId()));

        List<PositionEntity> positionEntities = findByFilter(positionSearchModel).toList();

        WorktypeEntity worktypeEntity =
                positionWorktypeService.findWorktypeByName(updateData.getWorktype().getName());

        if (positionEntities.size() == 1) {
            positionEntity = positionEntities.get(0);
            updateData(positionEntity, updateData);
            positionEntity.setWorktype(worktypeEntity);
        } else if (positionEntities.isEmpty()) {
            throw new NoMatchingEntryInDatabaseException("Position with name " + updateData.getName() + " not found in database");
        } else {
            throw new NoMatchingEntryInDatabaseException("Position with this name and id not found in database");
        }

        PositionEntity savedPosition = positionRepository.save(positionEntity);

        return positionRepository.findPositionEntityByName(savedPosition.getName());
    }

    private void updateData(PositionEntity positionToUpdate, PositionEntity updateData){

        positionToUpdate.setName(updateData.getName());
        positionToUpdate.setWorktype(updateData.getWorktype());
        positionToUpdate.setSalary(updateData.getSalary());

    }

    public WorktypeEntity worktypeCheck(WorktypeDTO worktypeDTO) {
        return positionWorktypeService.worktypeCheck(worktypeDTO);
    }

    @Transactional
    public void deleteById(Long id) {
        PositionEntity positionToDelete = findById(id);
        delete(positionToDelete);
    }

    private void delete(PositionEntity positionEntity) {
        Set<EmployeePositionRangeEntity> employeesWithPosition =
                emplPosRangeService.findActualEmployeeWithPosition(positionEntity.getId());

        if (employeesWithPosition.isEmpty()) {
            positionRepository.delete(positionEntity);
        } else {
            throw new PositionHasEmployeesException(
                    String.format("The position cannot be deleted. There are %d employees with this position",
                            employeesWithPosition.size()));
        }
    }

    public long getCountOfTotalItems() {
        return positionRepository.count();
    }
}
