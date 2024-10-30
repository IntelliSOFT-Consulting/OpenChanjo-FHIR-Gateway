/*
 * Copyright 2021-2023 Google LLC
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
package com.google.fhir.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import com.google.fhir.gateway.validators.PatientResourceValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Response;

/**
 * This is an example servlet that can be used for any custom endpoint. It does not make any
 * assumptions about authorization headers or accessing a FHIR server.
 */

@WebServlet(urlPatterns = "/chanjo-gateway/*")
public class CustomGenericEndpointExample extends HttpServlet {

  private final ApiServiceImpl apiService = new ApiServiceImpl();
  private final TokenVerifier tokenVerifier;
  private final HttpFhirClient fhirClient;

  private String BASE_URL_FHIR = "chanjo-hapi/fhir/";
  private String BASE_URL_AUTH = "auth/";

  private final ResourceValidatorFactory validatorFactory = new ResourceValidatorFactory();


  public CustomGenericEndpointExample() throws IOException {
    this.tokenVerifier = TokenVerifier.createFromEnvVars();
    this.fhirClient = FhirClientFactory.createFhirClientFromEnvVars();
  }

    @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    forwardRequest(req, resp, "GET");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    forwardRequest(req, resp, "POST");
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    forwardRequest(req, resp, "PUT");
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    forwardRequest(req, resp, "DELETE");
  }

  /**
   * This function forwards an HTTP request to a specified URL (Service B) and returns the response.//+
   *
   * @param req    The incoming HTTP request.//+
   * @param resp
   * @param method The HTTP method of the incoming request (e.g., GET, POST, PUT, DELETE).//+
   *///+
  private void forwardRequest(HttpServletRequest req, HttpServletResponse resp, String method) throws IOException {
    // Get the URL path after "/proxy" and append it to Service B's base URL

    ObjectMapper mapper = new ObjectMapper();
    resp.setContentType("application/json");

    DbResults dbResults = null;
    int statusCode = HttpServletResponse.SC_BAD_REQUEST;

    // Check the Bearer token to be a valid JWT with required claims.
    String authHeader = req.getHeader("Authorization");
    if (authHeader == null) {
      statusCode = HttpServletResponse.SC_UNAUTHORIZED;
      String responseString = "Authorization header not found.";
      dbResults = new DbResults(responseString);

    }else {
      DecodedJWT jwt = tokenVerifier.decodeAndVerifyBearerToken(authHeader);

      DbUser dbUser = getProviderInfoFromJwt(authHeader);
      if (dbUser == null) {
        statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String responseString = "Invalid or expired JWT.";
        dbResults = new DbResults(responseString);
      }else {

        /**
         * Use the jwt to get user roles and permissions to determine access to fhir resources.
         * TODO: Amolo should include the roles and permissions in the jwt
         */
        String practitionerRole = dbUser.getPractitionerRole();
        String fhirPractitionerId = dbUser.getFhirPractitionerId();
        String facility = dbUser.getFacility();

        try{

          String path = req.getRequestURI().substring(req.getContextPath().length() + "/chanjo-gateway/".length());
          String queryString = req.getQueryString() != null ? "?" + req.getQueryString() : "";
          String targetUrl = BASE_URL_FHIR + path + queryString;

          String resourceType = path.split("/")[0];

          ResourceValidator resourceValidator = validatorFactory.getValidator(resourceType);

          if (resourceValidator != null){

            Call<Object> call = null;
            switch (method) {
              case "GET":
                call = resourceValidator.getResource(practitionerRole, targetUrl);
                break;
              case "POST":
                call = resourceValidator.createResource(practitionerRole, targetUrl, req);
                break;
              case "PUT":
                call = resourceValidator.updateResource(practitionerRole, targetUrl, req);
                break;
              case "DELETE":
                call = resourceValidator.deleteResource(practitionerRole, targetUrl);
                break;
              default:
            }

            if (call == null) {
              statusCode = HttpServletResponse.SC_UNAUTHORIZED;
              String responseString = "The resource does not exist or you do not have access to it.";
              dbResults = new DbResults(responseString);
            }else {

              Response<Object> response = call.execute();
              int statusCodeRes = response.code();

              if (response.isSuccessful()) {
                statusCode = HttpServletResponse.SC_OK;
                dbResults = new DbResults(response.body());

              } else {

                if (statusCodeRes == 404){

                  statusCode = HttpServletResponse.SC_NOT_FOUND;
                  String responseString = "Resource not found";
                  dbResults = new DbResults(responseString);

                }else {

                  String responseString = "Check the request and try again";
                  dbResults = new DbResults(responseString);

                }

              }

            }

          }else {
            String responseString =  "Check the request and try again";
            dbResults = new DbResults(responseString);
          }

        }catch (Exception e){
          e.printStackTrace();

          statusCode = HttpServletResponse.SC_BAD_REQUEST;
          String responseString =  "Check the request and try again";
          dbResults = new DbResults(responseString);

        }
      }

    }

    resp.setStatus(statusCode);
    if (statusCode == HttpServletResponse.SC_OK) {
      mapper.writeValue(resp.getWriter(), dbResults.getDetails());
    }else {
      mapper.writeValue(resp.getWriter(), dbResults);
    }

  }

  private DbUser getProviderInfoFromJwt(String jwt) {

    try{
      Call<UserResponse> call = apiService.getProviderInfo(jwt);
      Response<UserResponse> response = call.execute();
      if (response.isSuccessful()) {
        UserResponse userResponse = response.body();
          if (userResponse != null) {
            String status = userResponse.getStatus();
            if (status.equals("success")) {
              return userResponse.getUser();

            }
          }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return null;

  }


  private String readResponseBody(HttpURLConnection connection) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      return response.toString();
    }
  }

  private String readRequestBody(HttpServletRequest request) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
    }
    return stringBuilder.toString();
  }

  private void copyHeaders(HttpServletRequest req, HttpURLConnection connection) {
    Enumeration<String> headerNames = req.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      Enumeration<String> headers = req.getHeaders(headerName);
      while (headers.hasMoreElements()) {
        String headerValue = headers.nextElement();
        connection.setRequestProperty(headerName, headerValue);
      }
    }
  }

  private void copyResponseHeaders(HttpURLConnection connection, HttpServletResponse resp) {
    for (String headerKey : connection.getHeaderFields().keySet()) {
      if (headerKey != null) {
        resp.setHeader(headerKey, connection.getHeaderField(headerKey));
      }
    }
  }

  private void writeResponseBody(HttpURLConnection connection, HttpServletResponse resp) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      resp.getWriter().write(response.toString());
    }
  }
}
