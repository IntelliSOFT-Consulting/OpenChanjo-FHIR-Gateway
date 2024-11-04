package com.google.fhir.gateway.dynamic_validators;

public class DynamicResourceValidatorFactory {

    private final DynamicResourceRoleLoader roleLoader;

    public DynamicResourceValidatorFactory(DynamicResourceRoleLoader roleLoader) {
        this.roleLoader = roleLoader;
    }

    // Factory method to create a validator for any given resource
    public DynamicBaseResourceValidator createValidator(String resourceName) {
        return new DynamicBaseResourceValidator(roleLoader, resourceName);
    }
}

