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
package com.google.fhir.gateway.dynamic_validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class DynamicResourceRoleLoader {

    private final JsonNode resourcesNode;

    public DynamicResourceRoleLoader() throws IOException {
        // Load the JSON file from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("roles-config.json");
        if (inputStream == null) {
            throw new IOException("Could not find 'roles-config.json' in the classpath.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);
        this.resourcesNode = rootNode.path("resources");
    }


    // Method to fetch roles for a given resource and operation (create, update, delete, get)
    public Set<String> getRolesForOperation(String resourceName, String operation) {
        Set<String> roles = new HashSet<>();
        JsonNode resourceNode = resourcesNode.path(resourceName);
        if (!resourceNode.isMissingNode()) {
            JsonNode operationNode = resourceNode.path(operation);
            if (operationNode.isArray()) {
                for (JsonNode roleNode : operationNode) {
                    roles.add(roleNode.asText());
                }
            }
        }
        return roles;
    }
}

