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
package com.google.fhir.gateway.json_file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "config")
public class RolesConfig {

    private BaseUrl baseUrl;
    private List<String> roles;
    private List<String> availableResources;
    private Map<String, ResourcePermissions> resources;

    // Getters and setters
    public BaseUrl getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(BaseUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(List<String> availableResources) {
        this.availableResources = availableResources;
    }

    public Map<String, ResourcePermissions> getResources() {
        return resources;
    }

    public void setResources(Map<String, ResourcePermissions> resources) {
        this.resources = resources;
    }

    // BaseUrl inner class
    public static class BaseUrl {
        private String platform;
        private String url;
        private String fhir;
        private String auth;
        private String tokenProvider;

        //Getter and Setters for tokenProvider
        public String getTokenProvider() {
            return tokenProvider;
        }
        public void setTokenProvider(String tokenProvider) {
            this.tokenProvider = tokenProvider;
        }


        // Getters and setters
        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFhir() {
            return fhir;
        }

        public void setFhir(String fhir) {
            this.fhir = fhir;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }

    // ResourcePermissions inner class
    public static class ResourcePermissions {
        private List<String> create;
        private List<String> update;
        private List<String> delete;
        private List<String> get;

        // Getters and setters
        public List<String> getCreate() {
            return create;
        }

        public void setCreate(List<String> create) {
            this.create = create;
        }

        public List<String> getUpdate() {
            return update;
        }

        public void setUpdate(List<String> update) {
            this.update = update;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
        }

        public List<String> getGet() {
            return get;
        }

        public void setGet(List<String> get) {
            this.get = get;
        }
    }
}
