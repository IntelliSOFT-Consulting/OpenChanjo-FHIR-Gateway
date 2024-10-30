package com.google.fhir.gateway.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

public interface ResourceValidator {
    Call<Object> getResource(String role, String targetUrl);
    Call<Object> createResource(String role, String targetUrl, HttpServletRequest requestBody);
    Call<Object> updateResource(String role, String targetUrl , HttpServletRequest requestBody);
    Call<Object> deleteResource(String role, String targetUrl);
}

