package ru.klokov.employeesdatasystem.security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.dto.CreateSecurityUserDTO;
import ru.klokov.employeesdatasystem.security.dto.SecurityUserDTO;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;
import ru.klokov.employeesdatasystem.security.mappers.CreateSecurityUserEntityDTOMapper;
import ru.klokov.employeesdatasystem.security.mappers.SecurityUserEntityDTOMapper;
import ru.klokov.employeesdatasystem.security.services.SecurityUserDetailsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/common/security")
@RequiredArgsConstructor
public class SecurityUserController {

    private final SecurityUserDetailsManager service;
    private final SecurityUserEntityDTOMapper securityUserEntityDTOMapper;
    private final CreateSecurityUserEntityDTOMapper createSecurityUserEntityDTOMapper;

    @GetMapping
    @PreAuthorize("hasPermission('user', 'read')")
    public List<SecurityUserDTO> findAll() {
        List<SecurityUser> allUsers = service.findAll();
        List<SecurityUserDTO> result = new ArrayList<>();

        for (SecurityUser user : allUsers) {
            result.add(securityUserEntityDTOMapper.convertFromEntity(user));
        }
        return result;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('user', 'readById')")
    public SecurityUserDTO getById(@PathVariable("id")Long id) {
        SecurityUser securityUser;

        try {
            securityUser = service.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoMatchingEntryInDatabaseException("Unable to find user with id = " + id);
        }
        return securityUserEntityDTOMapper.convertFromEntity(securityUser);
    }

    @PostMapping
    @PreAuthorize("hasPermission('user', 'create')")
    public SecurityUserDTO addNewUser(@RequestBody CreateSecurityUserDTO dto) {
        CreateSecurityUserEntity createSecurityUserEntity =
                createSecurityUserEntityDTOMapper.convertFromDTO(dto);

        SecurityUser createdUser = service.createUser(createSecurityUserEntity);

        return securityUserEntityDTOMapper.convertFromEntity(createdUser);
    }
}
