package com.google.fhir.gateway.validators;

import com.google.fhir.gateway.ApiServiceImpl;
import com.google.fhir.gateway.interfaces.AccessChecker;
import com.google.fhir.gateway.interfaces.AccessDecision;
import com.google.fhir.gateway.interfaces.RequestDetailsReader;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImmunizationResourceValidator implements AccessChecker, ResourceValidator {

    private final ApiServiceImpl apiService = new ApiServiceImpl();

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
        return apiService.createResource(targetUrl, readRequestBody(requestBody));
    }

    @Override
    public Call<Object> updateResource(String role, String targetUrl, HttpServletRequest requestBody) {
        return apiService.updateResource(targetUrl, readRequestBody(requestBody));
    }

    @Override
    public Call<Object> deleteResource(String role, String targetUrl) {
        return apiService.deleteResource(targetUrl);
    }

    private String readRequestBody(HttpServletRequest request) {

        try{

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            return stringBuilder.toString();

        }catch (IOException e) {
            e.printStackTrace();
            return null;  // or throw an exception here depending on your needs.
        }
    }
}
