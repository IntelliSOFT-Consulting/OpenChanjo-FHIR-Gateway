package com.google.fhir.gateway

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FormatterClass() {

        /**
     * Reads the body of an HTTP request and returns it as a string.
     *
     * @param request The HttpServletRequest object containing the client's request.
     * @return A string representing the body of the request, or null if an IOException occurs.
     */
    fun readRequestBody(request: HttpServletRequest): String? {
        try {
            val stringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(request.inputStream, "utf-8")).use { reader ->
                var line: String?
                while ((reader.readLine().also { line = it }) != null) {
                    stringBuilder.append(line)
                }
            }
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null // or throw an exception here depending on your needs.
        }
    }

        /**
     * Constructs a ResponseEntity based on the status code and details provided in the Results object.
     *
     * @param addedResults The Results object containing the status code and details for the response.
     * @return A ResponseEntity object with the appropriate HTTP status and body content based on the status code.
     */
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

}