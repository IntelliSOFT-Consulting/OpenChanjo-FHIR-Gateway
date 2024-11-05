package com.google.fhir.gateway

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class ConfigService {

    val objectMapper = ObjectMapper()

    private fun loadRolesConfig(key:String): JsonNode? {
        // Load the JSON file from the classpath
        val inputStream = javaClass.classLoader.getResourceAsStream("roles-config.json")
            ?: throw IOException("Could not find 'roles-config.json' in the classpath.")

        val rootNode = objectMapper.readTree(inputStream)
        return rootNode.path(key)
    }

    fun printBaseUrl(key: String): RolesConfig.BaseUrl? {
        val rolesConfig = loadRolesConfig(key)

        // convert the above to JSON
        val json = objectMapper.writeValueAsString(rolesConfig)

        //Convert the JSON back to a RolesConfig object
        if (key == "baseUrl"){
            val rolesConfigObj = objectMapper
                .readValue(json, RolesConfig.BaseUrl::class.java)
            return rolesConfigObj
        }
        return null
    }
}