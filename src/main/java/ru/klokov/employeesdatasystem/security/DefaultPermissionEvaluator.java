package ru.klokov.employeesdatasystem.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DefaultPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object resource, Object action) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        SecurityPermission permission = new SecurityPermission(resource + "." + action);
        return user.getAuthorities().contains(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
