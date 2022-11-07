package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionWorktypeService {

    private final WorktypeRepository worktypeRepository;
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public WorktypeEntity findWorktypeByName(String name) {
        Optional<WorktypeEntity> foundWorktype;

        if (name == null || name.isEmpty()) {
            throw new NullOrEmptyArgumentexception("Worktype name can't be null or empty string");
        } else {
            foundWorktype = worktypeRepository.findWorktypeEntityByName(name);
        }

        if (foundWorktype.isPresent()) {
            return foundWorktype.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Worktype with name =  " + name + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public WorktypeEntity findWorktypeById(Long id) {
        Optional<WorktypeEntity> foundWorktype;

        if (id == null) {
            throw new NullOrEmptyArgumentexception("Id can't be null");
        } else {
            foundWorktype = worktypeRepository.findById(id);
        }

        if (foundWorktype.isPresent()) {
            return foundWorktype.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Worktype wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public PositionEntity findPositionByName(String name) {
        return positionRepository.findPositionEntityByName(name);
    }

    @Transactional(readOnly = true)
    public WorktypeEntity worktypeCheck(WorktypeDTO worktypeDTO) {
        if (worktypeDTO == null) {
            return null;
        }

        WorktypeEntity worktypeEntity = null;

        if (worktypeDTO.getName() != null) {
            worktypeEntity = findWorktypeByName(worktypeDTO.getName());
        }

        if (worktypeEntity == null) {
            if (worktypeDTO.getId() == null) {
                return worktypeEntity;
            }
            Long worktypeId = worktypeDTO.getId();
            try {
                worktypeEntity = findWorktypeById(worktypeId);
            } catch (NoSuchElementException e) {
                return null;
            }
        }
        return worktypeEntity;
    }

    @Transactional(readOnly = true)
    public WorktypeEntity worktypeCheckByName(String worktypeName) {
        if (worktypeName == null) {
            return null;
        }

        WorktypeEntity worktypeEntity = null;

        try {
            worktypeEntity = findWorktypeByName(worktypeName);
        } catch (NoSuchElementException e) {
            return null;
        }

        return worktypeEntity;
    }
}
