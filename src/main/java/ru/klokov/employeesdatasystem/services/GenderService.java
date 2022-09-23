package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.GenderRepository;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GenderSpecification;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GendersSearchModel;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;
    private final SortColumnChecker sortColumnChecker;

    @Transactional(readOnly = true)
    public List<GenderEntity> findAll() {
        return genderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GenderEntity findById(Long id) {
        Optional<GenderEntity> gender;

        if (id == null) {
            throw new NullOrEmptyArgumentexception("Id can't be null");
        } else {
            gender = genderRepository.findById(id);
        }

        if (gender.isPresent()) {
            return gender.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Gender wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public GenderEntity findGenderByName(String name) {
        Optional<GenderEntity> gender;

        if (name == null || name.isEmpty()) {
            throw new NullOrEmptyArgumentexception("Name can't be null or empty string");
        } else {
            gender = genderRepository.findGenderEntityByName(name);
        }

        if (gender.isPresent()) {
            return gender.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Gender wit name =  " + name + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public Page<GenderEntity> findByFilter(GendersSearchModel request) {
        Sort sort = sortColumnChecker.sortColumnCheck(request);

        int page = request.getPages() != null ? request.getPages() : 0;
        int size = (request.getLimit() != null && request.getLimit() != 0) ? request.getLimit() : 5;

        Pageable pageable = PageRequest.of(page, size, sort);

        if (request.getIds().isEmpty() && request.getNames().isEmpty()) {
            return Page.empty();
        } else {
            return genderRepository.findAll(new GenderSpecification(request), pageable);
        }

    }

    public long getCountOfTotalItems() {
        return genderRepository.count();
    }

}
