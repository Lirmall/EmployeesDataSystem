package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GenderSpecification;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.gendersSpecification.WorktypeSearchModel;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.gendersSpecification.WorktypeSpecification;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorktypeService {

    private final WorktypeRepository worktypeRepository;
    private final SortColumnChecker sortColumnChecker;

    @Transactional(readOnly = true)
    public List<WorktypeEntity> findAll() {
        return worktypeRepository.findAll();
    }

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
    public WorktypeEntity findWorktypeByName(String name) {
        Optional<WorktypeEntity> foundWorktype;

        if (name == null || name.isEmpty()) {
            throw new NullOrEmptyArgumentexception("Name can't be null or empty string");
        } else {
            foundWorktype = worktypeRepository.findWorktypeEntityByName(name);
        }

        if (foundWorktype.isPresent()) {
            return foundWorktype.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Worktype wit name =  " + name + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public Page<WorktypeEntity> findByFilter(WorktypeSearchModel request) {
        Sort sort = sortColumnChecker.worktypeSortColumnCheck(request);

        int page = request.getPages() != null ? request.getPages() : 0;
        int size = request.getLimit() != null ? request.getLimit() : 5;

        Pageable pageable = PageRequest.of(page, size, sort);

        if (request.getIds().isEmpty() && request.getNames().isEmpty()) {
            return Page.empty();
        } else {
            return worktypeRepository.findAll(new WorktypeSpecification(request), pageable);
        }
    }

    public long getCountOfTotalItems() {
        return worktypeRepository.count();
    }
}
