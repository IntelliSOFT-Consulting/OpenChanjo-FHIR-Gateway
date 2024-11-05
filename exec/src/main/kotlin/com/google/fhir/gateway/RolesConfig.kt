package com.google.fhir.gateway

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "config")
class RolesConfig {

    var baseUrl: BaseUrl? = null
    var roles: List<String>? = null
    var availableResources: List<String>? = null
    var resources: Map<String, ResourcePermissions>? = null

    data class BaseUrl(
        var platform: String? = null,
        var url: String? = null,
        var fhir: String? = null,
        var auth: String? = null
    )

    data class ResourcePermissions(
        var create: List<String>? = null,
        var update: List<String>? = null,
        var delete: List<String>? = null,
        var get: List<String>? = null
    )
}