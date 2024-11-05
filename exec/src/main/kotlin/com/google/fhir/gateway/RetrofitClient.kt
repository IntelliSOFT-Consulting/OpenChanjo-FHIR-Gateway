package com.google.fhir.gateway

import com.google.fhir.gateway.RolesConfig.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A client for creating and managing a Retrofit instance configured with a base URL and a Gson converter.
 *
 * @property BASE_URL The base URL for the API endpoints.
 * @property retrofit The Retrofit instance configured with the base URL and Gson converter.
 * @property apiService The API service interface created from the Retrofit instance.
 */
class RetrofitClient {

    private val configService = ConfigService()
    var urlBaseUrl: BaseUrl? = configService.printBaseUrl("baseUrl")
    val baseUrl: String = urlBaseUrl?.url ?: "https://openchanjotest.intellisoftkenya.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}