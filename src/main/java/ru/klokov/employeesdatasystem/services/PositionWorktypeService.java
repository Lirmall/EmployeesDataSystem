package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionWorktypeService {

    private final WorktypeRepository worktypeRepository;

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
}
