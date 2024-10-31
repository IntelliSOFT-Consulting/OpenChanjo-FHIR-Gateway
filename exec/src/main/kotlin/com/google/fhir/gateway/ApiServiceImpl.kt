package com.google.fhir.gateway

import retrofit2.Call

class ApiServiceImpl : ApiService{

    private val apiService = RetrofitClient().apiService

    /**
     * Retrieves a resource from the specified URL.
     *
     * @param url The URL of the resource to retrieve.
     * @return A [Call] object that can be used to request the resource.
     */
    override fun getResource(url: String): Call<Any?> {
        return apiService.getResource(url)
    }

    /**
     * Creates a new resource at the specified URL with the given request body.
     *
     * @param url The URL where the resource should be created.
     * @param requestBody The body of the request containing the resource data.
     * @return A [Call] object that can be used to execute the creation request.
     */
    override fun createResource(url: String, requestBody: Any?): Call<Any?> {
        return apiService.createResource(url, requestBody)
    }

    /**
     * Updates an existing resource at the specified URL with the given request body.
     *
     * @param url The URL of the resource to update.
     * @param requestBody The body of the request containing the updated resource data.
     * @return A [Call] object that can be used to execute the update request.
     */
    override fun updateResource(url: String, requestBody: Any?): Call<Any?> {
        return apiService.updateResource(url, requestBody)
    }

    /**
     * Deletes a resource from the specified URL.
     *
     * @param url The URL of the resource to delete.
     * @return A [Call] object that can be used to execute the deletion request.
     */
    override fun deleteResource(url: String): Call<Any?> {
        return apiService.deleteResource(url)
    }

    /**
     * Retrieves provider information using the specified token.
     *
     * @param token The token used to authenticate the request for provider information.
     * @return A [Call] object that can be used to request the provider information.
     */
    override fun getProviderInfo(token: String): Call<UserResponse> {
        return apiService.getProviderInfo(token)
    }
}