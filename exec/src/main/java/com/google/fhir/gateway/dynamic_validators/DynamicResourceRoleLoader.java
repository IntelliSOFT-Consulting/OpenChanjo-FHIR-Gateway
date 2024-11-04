package com.google.fhir.gateway.dynamic_validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

