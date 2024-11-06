package com.google.fhir.gateway.retrofit;

import com.google.fhir.gateway.dtos.DbResults;
import com.google.fhir.gateway.dtos.Results;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FormatterClass {

    /**
     * Reads the body of an HTTP request and returns it as a string.
     *
     * @param request The HttpServletRequest object containing the client's request.
     * @return A string representing the body of the request, or null if an IOException occurs.
     */
    public String readRequestBody(HttpServletRequest request) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or throw an exception here depending on your needs.
        }
    }

    /**
     * Constructs a ResponseEntity based on the status code and details provided in the Results object.
     *
     * @param addedResults The Results object containing the status code and details for the response.
     * @return A ResponseEntity object with the appropriate HTTP status and body content based on the status code.
     */
    public ResponseEntity<?> getResponseEntity(Results addedResults) {
        int statusCode = addedResults.getStatusCode();
        Object results = addedResults.getDetails();
        switch (statusCode) {
            case 201:
                return new ResponseEntity<>(results, HttpStatus.CREATED);
            case 200:
                return new ResponseEntity<>(results, HttpStatus.OK);
            case 400:
                return ResponseEntity.badRequest().body(new DbResults(results.toString()));
            default:
                return ResponseEntity.badRequest().body(new DbResults(results.toString()));
        }
    }
}
