//package com.google.fhir.gateway.resource_validators;
//
//import com.google.fhir.gateway.ApiServiceImpl;
//import com.google.fhir.gateway.FormatterClass;
//import com.google.fhir.gateway.OpenChanjoRoles;
//import com.google.fhir.gateway.interfaces.*;
//import com.google.fhir.gateway.validators.ResourceRoleValidator;
//import jakarta.servlet.http.HttpServletRequest;
//import retrofit2.Call;
//
//import java.util.Set;
//
///**
// * This class implements the AccessChecker and ResourceValidator interfaces to validate and access AdverseEvent resources.
// * It uses role-based access control to determine whether a user has permission to perform specific operations on the resources.
// *
// * @author David Njau
// * @version 1.0
// * @since 2023-01-01
// */
//public class AdverseEventResourceValidator implements AccessChecker, ResourceValidator {
//
//    private final ApiServiceImpl apiService = new ApiServiceImpl();
//    private final FormatterClass formatter = new FormatterClass();
//
//    // Define allowed roles for different operations
//    private final Set<String> createAllowedRoles = Set.of(
//            OpenChanjoRoles.NURSE.name()
//    );
//    private final Set<String> updateAllowedRoles = Set.of(
//            OpenChanjoRoles.NURSE.name()
//    );
//    private final Set<String> deleteAllowedRoles = Set.of(
//            OpenChanjoRoles.NURSE.name()
//    );
//    private final Set<String> getAllowedRoles = Set.of(
//            OpenChanjoRoles.NURSE.name(),
//            OpenChanjoRoles.ADMINISTRATOR.name(),
//            OpenChanjoRoles.NATIONAL_SYSTEM_ADMINISTRATOR.name()
//    );
//
//    private final Set<String> testAllowedRoles = Set.of(OpenChanjoRoles.FACILITY_SYSTEM_ADMINISTRATOR.name());
//
//    // Instantiate role validators dynamically
//    private final RoleValidator createResourceRoleValidator = new ResourceRoleValidator(createAllowedRoles);
//    private final RoleValidator updateResourceRoleValidator = new ResourceRoleValidator(updateAllowedRoles);
//    private final RoleValidator deleteResourceRoleValidator = new ResourceRoleValidator(deleteAllowedRoles);
//    private final RoleValidator getResourceRoleValidator = new ResourceRoleValidator(getAllowedRoles);
//    private final RoleValidator testResourceRoleValidator = new ResourceRoleValidator(testAllowedRoles);
//
//
//
//    @Override
//    public AccessDecision checkAccess(RequestDetailsReader requestDetails) {
//        return null;
//    }
//
//    /**
//     * Retrieves an AdverseEvent resource from the specified target URL.
//     *
//     * @param role The user's role.
//     * @param targetUrl The URL of the AdverseEvent resource to retrieve.
//     * @return A Retrofit Call object representing the asynchronous HTTP request to retrieve the resource.
//     *         If access is denied, returns null.
//     */
//    @Override
//    public Call<Object> getResource(String role, String targetUrl) {
//        if (!getResourceRoleValidator.hasAccess(role)) {
//            return null; // Return null if access is denied
//        }
//        return apiService.getResource(targetUrl);
//    }
//
//    /**
//     * Creates a new AdverseEvent resource at the specified target URL.
//     *
//     * @param role The user's role.
//     * @param targetUrl The URL where the new AdverseEvent resource will be created.
//     * @param requestBody The request body containing the details of the new AdverseEvent resource.
//     * @return A Retrofit Call object representing the asynchronous HTTP request to create the resource.
//     *         If access is denied, returns null.
//     */
//    @Override
//    public Call<Object> createResource(String role, String targetUrl, HttpServletRequest requestBody) {
//        if (!createResourceRoleValidator.hasAccess(role)) {
//            return null; // Return null if access is denied
//        }
//        return apiService.createResource(targetUrl, formatter.readRequestBody(requestBody));
//    }
//
//    /**
//     * Updates an existing AdverseEvent resource at the specified target URL.
//     *
//     * @param role The user's role.
//     * @param targetUrl The URL of the AdverseEvent resource to update.
//     * @param requestBody The request body containing the updated details of the AdverseEvent resource.
//     * @return A Retrofit Call object representing the asynchronous HTTP request to update the resource.
//     *         If access is denied, returns null.
//     */
//    @Override
//    public Call<Object> updateResource(String role, String targetUrl, HttpServletRequest requestBody) {
//        if (!updateResourceRoleValidator.hasAccess(role)) {
//            return null; // Return null if access is denied
//        }
//        return apiService.updateResource(targetUrl, formatter.readRequestBody(requestBody));
//    }
//
//    /**
//     * Deletes an AdverseEvent resource from the specified target URL.
//     *
//     * @param role The user's role.
//     * @param targetUrl The URL of the AdverseEvent resource to delete.
//     * @return A Retrofit Call object representing the asynchronous HTTP request to delete the resource.
//     *         If access is denied, returns null.
//     */
//    @Override
//    public Call<Object> deleteResource(String role, String targetUrl) {
//        if (!deleteResourceRoleValidator.hasAccess(role)) {
//            return null; // Return null if access is denied
//        }
//        return apiService.deleteResource(targetUrl);
//    }
//
//}
