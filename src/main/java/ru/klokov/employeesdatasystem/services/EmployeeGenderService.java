package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.GenderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeGenderService {
    private final GenderRepository genderRepository;

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
}
