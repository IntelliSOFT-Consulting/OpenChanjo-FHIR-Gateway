package com.google.fhir.gateway

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity



fun getResponseEntity(addedResults: Results): ResponseEntity<*> {
    val statusCode = addedResults.statusCode
    val results = addedResults.details
    return when (statusCode) {
        201 -> {
            ResponseEntity(results, HttpStatus.CREATED)
        }
        200 -> {
            ResponseEntity(results, HttpStatus.OK)
        }
        400 -> {
            ResponseEntity.badRequest().body(DbResults(results.toString()))
        }
        else -> {
            ResponseEntity.badRequest().body(DbResults(results.toString()))
        }
    }
}