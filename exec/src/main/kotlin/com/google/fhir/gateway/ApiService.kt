package com.google.fhir.gateway

import org.springframework.web.bind.annotation.RequestHeader
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * Retrieves a resource from the specified URL.
     *
     * @param url The URL of the resource to be retrieved.
     * @return A [Call] object that can be used to request the resource, returning a nullable [Any] type.
     */
    @GET
    fun getResource(@Url url: String): Call<Any?>

    /**
     * Creates a new resource at the specified URL with the provided request body.
     *
     * @param url The URL where the resource will be created.
     * @param requestBody The body of the request containing the resource data.
     * @return A [Call] object that can be used to execute the request, returning a nullable [Any] type.
     */
    @POST
    fun createResource(@Url url: String, @Body requestBody: Any?): Call<Any?>

    /**
     * Updates an existing resource at the specified URL with the provided request body.
     *
     * @param url The URL of the resource to be updated.
     * @param requestBody The body of the request containing the updated resource data.
     * @return A [Call] object that can be used to execute the request, returning a nullable [Any] type.
     */
    @PUT
    fun updateResource(@Url url: String, @Body requestBody: Any?): Call<Any?>

    /**
     * Deletes a resource from the specified URL.
     *
     * @param url The URL of the resource to be deleted.
     * @return A [Call] object that can be used to execute the request, returning a nullable [Any] type.
     */
    @DELETE
    fun deleteResource(@Url url: String): Call<Any?>

    /**
     * Retrieves information about the provider using the provided authorization token.
     *
     * @param token The Bearer token used for authorization.
     * @return A [Call] object that can be used to request the provider information, returning a [UserResponse].
     */
    @GET("auth/provider/me")
    fun getProviderInfo(
        @Header("Authorization") token: String,
    ): Call<UserResponse>
}