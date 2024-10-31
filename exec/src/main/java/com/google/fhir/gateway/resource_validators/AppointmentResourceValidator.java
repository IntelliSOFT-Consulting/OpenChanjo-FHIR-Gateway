package com.google.fhir.gateway.resource_validators;

import com.google.fhir.gateway.ApiServiceImpl;
import com.google.fhir.gateway.FormatterClass;
import com.google.fhir.gateway.OpenChanjoRoles;
import com.google.fhir.gateway.interfaces.*;
import com.google.fhir.gateway.validators.ResourceRoleValidator;
import jakarta.servlet.http.HttpServletRequest;
import retrofit2.Call;

import java.util.Set;

public class AppointmentResourceValidator implements AccessChecker, ResourceValidator {

    private final ApiServiceImpl apiService = new ApiServiceImpl();
    private final FormatterClass formatter = new FormatterClass();

    // Define allowed roles for different operations
    private final Set<String> createAllowedRoles = Set.of(
            OpenChanjoRoles.NURSE.name()
    );
    private final Set<String> updateAllowedRoles = Set.of(
            OpenChanjoRoles.NURSE.name()
    );
    private final Set<String> deleteAllowedRoles = Set.of(
            OpenChanjoRoles.NURSE.name()
    );
    private final Set<String> getAllowedRoles = Set.of(
            OpenChanjoRoles.NURSE.name(),
            OpenChanjoRoles.ADMINISTRATOR.name(),
            OpenChanjoRoles.NATIONAL_SYSTEM_ADMINISTRATOR.name()
    );

    private final Set<String> testAllowedRoles = Set.of(OpenChanjoRoles.FACILITY_SYSTEM_ADMINISTRATOR.name());

    // Instantiate role validators dynamically
    private final RoleValidator createResourceRoleValidator = new ResourceRoleValidator(createAllowedRoles);
    private final RoleValidator updateResourceRoleValidator = new ResourceRoleValidator(updateAllowedRoles);
    private final RoleValidator deleteResourceRoleValidator = new ResourceRoleValidator(deleteAllowedRoles);
    private final RoleValidator getResourceRoleValidator = new ResourceRoleValidator(getAllowedRoles);
    private final RoleValidator testResourceRoleValidator = new ResourceRoleValidator(testAllowedRoles);



        /**
     * Checks access permissions for a given request.
     *
     * @param requestDetails the details of the request to be checked for access permissions.
     *                       This includes information such as the request method, headers, and other relevant data.
     * @return an AccessDecision object representing the decision on whether access is granted or denied.
     *         Currently, this method returns null, indicating that no access decision is made.
     */
    @Override
    public AccessDecision checkAccess(RequestDetailsReader requestDetails) {
        return null;
    }

        /**
     * Retrieves a resource from the specified target URL if the role has access permissions.
     *
     * @param role      the role of the user requesting the resource. This determines if the user has
     *                  the necessary permissions to access the resource.
     * @param targetUrl the URL of the resource to be retrieved.
     * @return a Call object representing the request to retrieve the resource. Returns null if the
     *         role does not have access permissions.
     */
    @Override
    public Call<Object> getResource(String role, String targetUrl) {
        if (!getResourceRoleValidator.hasAccess(role)) {
            return null; // Return null if access is denied
        }
        return apiService.getResource(targetUrl);
    }

        /**
     * Creates a resource at the specified target URL if the role has access permissions.
     *
     * @param role        the role of the user attempting to create the resource. This determines if the user
     *                    has the necessary permissions to perform the creation operation.
     * @param targetUrl   the URL where the resource is to be created.
     * @param requestBody the HTTP request body containing the data for the resource to be created.
     * @return a Call object representing the request to create the resource. Returns null if the role
     *         does not have access permissions.
     */
    @Override
    public Call<Object> createResource(String role, String targetUrl, HttpServletRequest requestBody) {
        if (!createResourceRoleValidator.hasAccess(role)) {
            return null; // Return null if access is denied
        }
        return apiService.createResource(targetUrl, formatter.readRequestBody(requestBody));
    }

    @Override
    public Call<Object> updateResource(String role, String targetUrl, HttpServletRequest requestBody) {
        if (!updateResourceRoleValidator.hasAccess(role)) {
            return null; // Return null if access is denied
        }
        return apiService.updateResource(targetUrl, formatter.readRequestBody(requestBody));
    }

    @Override
    public Call<Object> deleteResource(String role, String targetUrl) {
        if (!deleteResourceRoleValidator.hasAccess(role)) {
            return null; // Return null if access is denied
        }
        return apiService.deleteResource(targetUrl);
    }

}
