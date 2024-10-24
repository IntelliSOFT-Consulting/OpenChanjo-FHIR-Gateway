package com.google.fhir.gateway

import org.springframework.web.bind.annotation.RequestHeader
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET
    fun getResource(@Url url: String): Call<Any?>

    @POST
    fun createResource(@Url url: String, @Body requestBody: Any?): Call<Any?>

    @PUT
    fun updateResource(@Url url: String, @Body requestBody: Any?): Call<Any?>

    @DELETE
    fun deleteResource(@Url url: String): Call<Any?>

    @GET("auth/provider/me")
    fun getProviderInfo(
        @Header("Authorization") token: String, // Add this line to pass the Bearer Token
    ): Call<UserResponse>
}