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
    Call<Object> getResource(String role, String targetUrl);

    /**
     * Creates a new resource using the specified role, target URL, and request body.
     *
     * @param role the role of the user creating the resource.
     * @param targetUrl the URL where the resource will be created.
     * @param requestBody the HTTP request body containing the resource data.
     * @return a Call object that can be used to execute the request and create the resource.
     */
    Call<Object> createResource(String role, String targetUrl, HttpServletRequest requestBody);

    /**
     * Updates an existing resource using the specified role, target URL, and request body.
     *
     * @param role the role of the user updating the resource.
     * @param targetUrl the URL of the resource to be updated.
     * @param requestBody the HTTP request body containing the updated resource data.
     * @return a Call object that can be used to execute the request and update the resource.
     */
    Call<Object> updateResource(String role, String targetUrl, HttpServletRequest requestBody);

    /**
     * Deletes a resource based on the specified role and target URL.
     *
     * @param role the role of the user requesting the deletion.
     * @param targetUrl the URL of the resource to be deleted.
     * @return a Call object that can be used to execute the request and delete the resource.
     */
    Call<Object> deleteResource(String role, String targetUrl);
}

