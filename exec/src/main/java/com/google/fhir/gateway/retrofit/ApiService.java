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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface ApiService {

    /**
     * Retrieves a resource from the specified URL.
     *
     * @param url The URL of the resource to be retrieved.
     * @return A Call object that can be used to request the resource, returning a nullable Any type.
     */
    @GET
    Call<Object> getResource(@Url String url);

    /**
     * Creates a new resource at the specified URL with the provided request body.
     *
     * @param url The URL where the resource will be created.
     * @param requestBody The body of the request containing the resource data.
     * @return A Call object that can be used to execute the request, returning a nullable Any type.
     */
    @POST
    Call<Object> createResource(@Url String url, @Body Object requestBody);

    /**
     * Updates an existing resource at the specified URL with the provided request body.
     *
     * @param url The URL of the resource to be updated.
     * @param requestBody The body of the request containing the updated resource data.
     * @return A Call object that can be used to execute the request, returning a nullable Any type.
     */
    @PUT
    Call<Object> updateResource(@Url String url, @Body Object requestBody);

    /**
     * Deletes a resource from the specified URL.
     *
     * @param url The URL of the resource to be deleted.
     * @return A Call object that can be used to execute the request, returning a nullable Any type.
     */
    @DELETE
    Call<Object> deleteResource(@Url String url);

    /**
     * Retrieves information about the provider using the provided authorization token.
     *
     * @param token The Bearer token used for authorization.
     * @return A Call object that can be used to request the provider information, returning a UserResponse.
     */
    @GET
    Call<UserResponse> getProviderInfo(@Url String url, @Header("Authorization") String token);
}

