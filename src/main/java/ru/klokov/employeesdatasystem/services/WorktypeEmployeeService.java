package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorktypeEmployeeService {

    private final WorktypeRepository worktypeRepository;
    private final SortColumnChecker sortColumnChecker;

    @Transactional(readOnly = true)
    public WorktypeEntity findById(Long id) {
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
    public WorktypeEntity findByName(String name) {
        Optional<WorktypeEntity> foundWorktype;

        if (name == null) {
            throw new NullOrEmptyArgumentexception("Id can't be null");
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
