package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeRangeService {

    private final RangeRepository rangeRepository;

    @Transactional(readOnly = true)
    public RangeEntity rangeCheck(RangeEntity range) {
        if (range.getId() != null) {
            return findRangeById(range.getId());
        } else if (!range.getName().isEmpty()) {
            return findRangeByName(range.getName());
        } else {
            throw new NoMatchingEntryInDatabaseException("Range not found");
        }
    }

    public RangeEntity findRangeById(Long id) {
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

    private RangeEntity findRangeByName(String name) {
        return rangeRepository.findRangeEntityByName(name);
    }
}
