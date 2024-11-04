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
import java.util.Enumeration;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.fhir.gateway.dynamic_validators.DynamicResourceRoleLoader;
import com.google.fhir.gateway.dynamic_validators.DynamicResourceValidatorFactory;
import com.google.fhir.gateway.dynamic_validators.ValidatorService;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

//  private final ResourceValidatorFactory validatorFactory = new ResourceValidatorFactory();


  private final DynamicResourceRoleLoader dynamicResourceRoleLoader = new DynamicResourceRoleLoader();
  private final DynamicResourceValidatorFactory dynamicResourceValidatorFactory =
          new DynamicResourceValidatorFactory(dynamicResourceRoleLoader);
  private final ValidatorService validatorService = new ValidatorService(dynamicResourceValidatorFactory);


    /**
   * Constructs a new instance of the CustomGenericEndpointExample servlet.
   * Initializes the token verifier and FHIR client using environment variables.
   *
   * @throws IOException if an error occurs during the creation of the token verifier
   *                     or FHIR client from environment variables.
   */
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
   * Forwards an HTTP request to a target FHIR server, validating the request and user permissions.
   * This method processes the request based on the HTTP method, validates the JWT token, and
   * determines access to FHIR resources using user roles and permissions.
   *
   * @param req   the HttpServletRequest object that contains the request the client has made
   *              of the servlet.
   * @param resp  the HttpServletResponse object that contains the response the servlet sends
   *              to the client.
   * @param method the HTTP method (GET, POST, PUT, DELETE) used for the request.
   * @throws IOException if an input or output exception occurs while processing the request.
   */
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

      try{
        tokenVerifier.decodeAndVerifyBearerToken(authHeader);

      }catch (Exception e){
        e.printStackTrace();
      }

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

          Call<Object> call = null;
          switch (method) {
            case "GET":
              call = validatorService.getResource(resourceType, method, practitionerRole, targetUrl);
              break;
            case "POST":
              call = validatorService.createResource(resourceType, method, practitionerRole, targetUrl, req);
              break;
            case "PUT":
              call = validatorService.updateResource(resourceType, method, practitionerRole, targetUrl, req);
              break;
            case "DELETE":
              call = validatorService.deleteResource(resourceType, method, practitionerRole, targetUrl);
              break;
            default:
          }

          if (call == null) {
            statusCode = HttpServletResponse.SC_FORBIDDEN;
            String responseString = "You do not have access to this resource.";
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

    /**
   * Retrieves provider information from a JWT token.
   * This method makes a call to an external service to obtain user details
   * associated with the provided JWT token.
   *
   * @param jwt the JSON Web Token (JWT) used to authenticate and retrieve user information.
   * @return a DbUser object containing the user details if the call is successful and the status is "success";
   *         otherwise, returns null.
   */
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

}
