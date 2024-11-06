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
package com.google.fhir.gateway.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

public interface ResourceValidator {

    /**
     * Retrieves a resource based on the specified role and target URL.
     *
     * @param role the role of the user requesting the resource.
     * @param targetUrl the URL of the resource to be retrieved.
     * @return a Call object that can be used to execute the request and obtain the resource.
     */
    Call<Object> getResource(String resource, String method, String role, String targetUrl);

    /**
     * Creates a new resource using the specified role, target URL, and request body.
     *
     * @param role the role of the user creating the resource.
     * @param targetUrl the URL where the resource will be created.
     * @param requestBody the HTTP request body containing the resource data.
     * @return a Call object that can be used to execute the request and create the resource.
     */
    Call<Object> createResource(String resource, String method, String role, String targetUrl, HttpServletRequest requestBody);

    /**
     * Updates an existing resource using the specified role, target URL, and request body.
     *
     * @param role the role of the user updating the resource.
     * @param targetUrl the URL of the resource to be updated.
     * @param requestBody the HTTP request body containing the updated resource data.
     * @return a Call object that can be used to execute the request and update the resource.
     */
    Call<Object> updateResource(String resource, String method, String role, String targetUrl, HttpServletRequest requestBody);

    /**
     * Deletes a resource based on the specified role and target URL.
     *
     * @param role the role of the user requesting the deletion.
     * @param targetUrl the URL of the resource to be deleted.
     * @return a Call object that can be used to execute the request and delete the resource.
     */
    Call<Object> deleteResource(String resource, String method, String role, String targetUrl);
}

