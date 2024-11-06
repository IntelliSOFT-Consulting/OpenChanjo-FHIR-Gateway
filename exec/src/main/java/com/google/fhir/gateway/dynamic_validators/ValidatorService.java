/*
 * Copyright 2021-2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.fhir.gateway.dynamic_validators;

import com.google.fhir.gateway.interfaces.AccessChecker;
import com.google.fhir.gateway.interfaces.AccessDecision;
import com.google.fhir.gateway.interfaces.RequestDetailsReader;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import com.google.fhir.gateway.retrofit.ApiServiceImpl;
import com.google.fhir.gateway.retrofit.FormatterClass;
import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

import java.io.IOException;

public class ValidatorService implements AccessChecker, ResourceValidator {

    private final DynamicResourceValidatorFactory validatorFactory;
    private final ApiServiceImpl apiService = new ApiServiceImpl();
    private final FormatterClass formatter = new FormatterClass();

    public ValidatorService(DynamicResourceValidatorFactory validatorFactory) throws IOException {
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

