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

import com.google.fhir.gateway.dtos.UserResponse;
import retrofit2.Call;

import java.io.IOException;

public class ApiServiceImpl implements ApiService {

    private ApiService apiService = new RetrofitClient().getApiService();

    public ApiServiceImpl() throws IOException {
    }

    /**
     * Retrieves a resource from the specified URL.
     *
     * @param url The URL of the resource to retrieve.
     * @return A Call object that can be used to request the resource.
     */
    @Override
    public Call<Object> getResource(String url) {
        return apiService.getResource(url);
    }

    /**
     * Creates a new resource at the specified URL with the given request body.
     *
     * @param url The URL where the resource should be created.
     * @param requestBody The body of the request containing the resource data.
     * @return A Call object that can be used to execute the creation request.
     */
    @Override
    public Call<Object> createResource(String url, Object requestBody) {
        return apiService.createResource(url, requestBody);
    }

    /**
     * Updates an existing resource at the specified URL with the given request body.
     *
     * @param url The URL of the resource to update.
     * @param requestBody The body of the request containing the updated resource data.
     * @return A Call object that can be used to execute the update request.
     */
    @Override
    public Call<Object> updateResource(String url, Object requestBody) {
        return apiService.updateResource(url, requestBody);
    }

    /**
     * Deletes a resource from the specified URL.
     *
     * @param url The URL of the resource to delete.
     * @return A Call object that can be used to execute the deletion request.
     */
    @Override
    public Call<Object> deleteResource(String url) {
        return apiService.deleteResource(url);
    }

    /**
     * Retrieves provider information using the specified token.
     *
     * @param token The token used to authenticate the request for provider information.
     * @return A Call object that can be used to request the provider information.
     */
    @Override
    public Call<UserResponse> getProviderInfo(String url, String token) {
        return apiService.getProviderInfo(url, token);
    }
}

