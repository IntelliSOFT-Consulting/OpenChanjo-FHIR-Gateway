package com.google.fhir.gateway.interfaces;

public interface RoleValidator {
    boolean hasAccess(String role);
}

