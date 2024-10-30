package com.google.fhir.gateway.resource_validators;

import com.google.fhir.gateway.ApiServiceImpl;
import com.google.fhir.gateway.FormatterClass;
import com.google.fhir.gateway.interfaces.AccessChecker;
import com.google.fhir.gateway.interfaces.AccessDecision;
import com.google.fhir.gateway.interfaces.RequestDetailsReader;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

public class ImmunizationResourceValidator implements AccessChecker, ResourceValidator {

    private final ApiServiceImpl apiService = new ApiServiceImpl();
    private final FormatterClass formatter = new FormatterClass();

    @Override
    public AccessDecision checkAccess(RequestDetailsReader requestDetails) {
        return null;
    }

    @Override
    public Call<Object> getResource(String role, String targetUrl) {
        return apiService.getResource(targetUrl);
    }

    @Override
    public Call<Object> createResource(String role, String targetUrl, HttpServletRequest requestBody) {
        return apiService.createResource(targetUrl, formatter.readRequestBody(requestBody));
    }

    @Override
    public Call<Object> updateResource(String role, String targetUrl, HttpServletRequest requestBody) {
        return apiService.updateResource(targetUrl, formatter.readRequestBody(requestBody));
    }

    @Override
    public Call<Object> deleteResource(String role, String targetUrl) {
        return apiService.deleteResource(targetUrl);
    }

}
