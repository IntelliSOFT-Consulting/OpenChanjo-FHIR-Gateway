package com.google.fhir.gateway.json_file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ConfigService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JsonNode loadRolesConfig(String key) throws IOException {
        // Load the JSON file from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("roles-config.json");
        if (inputStream == null) {
            throw new IOException("Could not find 'roles-config.json' in the classpath.");
        }

        JsonNode rootNode = objectMapper.readTree(inputStream);
        return rootNode.path(key);
    }

    public RolesConfig.BaseUrl printBaseUrl(String key) throws IOException {
        JsonNode rolesConfig = loadRolesConfig(key);

        // Convert the JsonNode to JSON string
        String json = objectMapper.writeValueAsString(rolesConfig);

        // Convert the JSON string back to a RolesConfig.BaseUrl object
        if ("baseUrl".equals(key)) {
            return objectMapper.readValue(json, RolesConfig.BaseUrl.class);
        }
        return null;
    }
}