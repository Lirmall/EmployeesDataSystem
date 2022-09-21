package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSearchModel;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSpecification;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSpecification;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RangeService {

    private final RangeRepository rangeRepository;
    private final SortColumnChecker sortColumnChecker;

    @Transactional(readOnly = true)
    public List<RangeEntity> findAll() {
        return rangeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RangeEntity findById(Long id) {
        Optional<RangeEntity> foundRange;

        if (id == null) {
            throw new NullOrEmptyArgumentexception("Id can't be null");
        } else {
            foundRange = rangeRepository.findById(id);
        }

        if (foundRange.isPresent()) {
            return foundRange.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Range wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public RangeEntity findRangeByName(String name) {
        return rangeRepository.findRangeEntityByName(name);
    }

    @Transactional(readOnly = true)
    public Page<RangeEntity> findByFilter(RangeSearchModel request) {
        Sort sort = sortColumnChecker.sortColumnCheck(request);

        int page = request.getPages() != null ? request.getPages() : 0;
        int size = request.getLimit() != null ? request.getLimit() : 5;

        Pageable pageable = PageRequest.of(page, size, sort);

        if (request.getIds().isEmpty() && request.getNames().isEmpty()) {
            return Page.empty();
        } else {
            return rangeRepository.findAll(new RangeSpecification(request), pageable);
        }
    }


    public long getCountOfTotalItems() {
        return rangeRepository.count();
    }
}
