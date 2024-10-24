package com.google.fhir.gateway

import retrofit2.Call

class ApiServiceImpl : ApiService{

    private val apiService = RetrofitClient().apiService

    override fun getResource(url: String): Call<Any?> {
        return apiService.getResource(url)
    }

    override fun createResource(url: String, requestBody: Any?): Call<Any?> {
        return apiService.createResource(url, requestBody)
    }

    override fun updateResource(url: String, requestBody: Any?): Call<Any?> {
        return apiService.updateResource(url, requestBody)
    }

    override fun deleteResource(url: String): Call<Any?> {
        return apiService.deleteResource(url)
    }

    override fun getProviderInfo(token: String): Call<UserResponse> {
        return apiService.getProviderInfo(token)
    }


}