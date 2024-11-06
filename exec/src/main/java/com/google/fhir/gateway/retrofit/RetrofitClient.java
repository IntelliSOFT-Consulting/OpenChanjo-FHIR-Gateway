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
package com.google.fhir.gateway.retrofit;

import com.google.fhir.gateway.json_file.ConfigService;
import com.google.fhir.gateway.json_file.RolesConfig;
import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A client for creating and managing a Retrofit instance configured with a base URL and a Gson converter.
 *
 * @property BASE_URL The base URL for the API endpoints.
 * @property retrofit The Retrofit instance configured with the base URL and Gson converter.
 * @property apiService The API service interface created from the Retrofit instance.
 */

// Singleton instance
public class RetrofitClient {

    private ConfigService configService = new ConfigService();
    private RolesConfig.BaseUrl urlBaseUrl = configService.printBaseUrl("baseUrl");
    private String baseUrl = (urlBaseUrl != null) ? urlBaseUrl.getUrl() : "https://openchanjotest.intellisoftkenya.com/";

    // Create an OkHttpClient if needed for custom settings like timeouts
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl) // Set the API base URL
                    .client(okHttpClient) // Optional: Use OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
            .build();

    @Getter
    private ApiService apiService = retrofit.create(ApiService.class);

    public RetrofitClient() throws IOException {
    }
}

