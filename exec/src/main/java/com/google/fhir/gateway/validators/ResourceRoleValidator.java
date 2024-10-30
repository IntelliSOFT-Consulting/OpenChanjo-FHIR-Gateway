package com.google.fhir.gateway.validators;

import com.google.fhir.gateway.interfaces.RoleValidator;

import java.util.Set;

public class ResourceRoleValidator implements RoleValidator {
    private final Set<String> allowedRoles;

    public ResourceRoleValidator(Set<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    @Override
    public boolean hasAccess(String role) {
        return allowedRoles.contains(role);
    }
}

