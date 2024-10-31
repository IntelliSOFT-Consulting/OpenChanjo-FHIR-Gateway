package com.google.fhir.gateway.validators;

import com.google.fhir.gateway.interfaces.RoleValidator;

import java.util.Set;

public class ResourceRoleValidator implements RoleValidator {
    private final Set<String> allowedRoles;

    public ResourceRoleValidator(Set<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    /**
     * Determines if the specified role has access based on the allowed roles.
     *
     * @param role the role to be checked for access.
     * @return {@code true} if the role is in the set of allowed roles, {@code false} otherwise.
     */
    @Override
    public boolean hasAccess(String role) {
        return allowedRoles.contains(role);
    }
}

