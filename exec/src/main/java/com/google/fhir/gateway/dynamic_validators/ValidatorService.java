package com.google.fhir.gateway.dynamic_validators;

import com.google.fhir.gateway.ApiServiceImpl;
import com.google.fhir.gateway.FormatterClass;
import com.google.fhir.gateway.interfaces.AccessChecker;
import com.google.fhir.gateway.interfaces.AccessDecision;
import com.google.fhir.gateway.interfaces.RequestDetailsReader;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

public class ValidatorService implements AccessChecker, ResourceValidator {

    private final DynamicResourceValidatorFactory validatorFactory;
    private final ApiServiceImpl apiService = new ApiServiceImpl();
    private final FormatterClass formatter = new FormatterClass();

    public ValidatorService(DynamicResourceValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    private boolean validateAccessOperation(
            String resource,
            String operation,
            String role) {
        DynamicBaseResourceValidator validator =
                validatorFactory.createValidator(resource);

        switch (operation) {
            case "POST":
                return validator.isRoleAllowedToCreate(role);
            case "PUT":
                return validator.isRoleAllowedToUpdate(role);
            case "DELETE":
                return validator.isRoleAllowedToDelete(role);
            case "GET":
                return validator.isRoleAllowedToGet(role);
            default:
                return false; // Return false if access is denied
        }
    }


    @Override
    public AccessDecision checkAccess(RequestDetailsReader requestDetails) {

        System.out.println("-----?");
        System.out.println("requestDetails = " + requestDetails);
        System.out.println("-----?");

        return null;
    }


    @Override
    public Call<Object> getResource(
            String resource,
            String method,
            String role,
            String targetUrl) {

        if (validateAccessOperation(resource, method, role)) {
            return apiService.getResource(targetUrl);
        }else {
            return null; // Return null if access is denied
        }

    }

    @Override
    public Call<Object> createResource(
            String resource,
            String method,
            String role,
            String targetUrl,
            HttpServletRequest requestBody) {
        if (validateAccessOperation(resource, method, role)) {
            return apiService.createResource(targetUrl, formatter.readRequestBody(requestBody));
        }else {
            return null; // Return null if access is denied
        }
    }

    @Override
    public Call<Object> updateResource(
            String resource,
            String method,
            String role,
            String targetUrl,
            HttpServletRequest requestBody) {
        if (validateAccessOperation(resource, method, role)) {
            return apiService.updateResource(targetUrl, formatter.readRequestBody(requestBody));
        }else {
            return null; // Return null if access is denied
        }
    }

    @Override
    public Call<Object> deleteResource(
            String resource,
            String method,
            String role,
            String targetUrl) {
        if (validateAccessOperation(resource, method, role)) {
            return apiService.deleteResource(targetUrl);
        } else {
            return null; // Return null if access is denied
        }
    }
}

