/*
 * Copyright 2021-2024 Google LLC
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
package com.google.fhir.gateway.dynamic_validators;

import java.util.Set;

public class DynamicBaseResourceValidator {

    protected final DynamicResourceRoleLoader roleLoader;
    private final String resourceName;

    // Constructor takes the resource name dynamically
    protected DynamicBaseResourceValidator(DynamicResourceRoleLoader roleLoader, String resourceName) {
        this.roleLoader = roleLoader;
        this.resourceName = resourceName;
    }

    // Utility methods to get roles for specific operations
    protected Set<String> getRolesForCreate() {
        return roleLoader.getRolesForOperation(resourceName, "create");
    }

    protected Set<String> getRolesForUpdate() {
        return roleLoader.getRolesForOperation(resourceName, "update");
    }

    protected Set<String> getRolesForDelete() {
        return roleLoader.getRolesForOperation(resourceName, "delete");
    }

    protected Set<String> getRolesForGet() {
        return roleLoader.getRolesForOperation(resourceName, "get");
    }

    // Additional validation methods for specific operations
    public boolean isRoleAllowedToCreate(String role) {
        return getRolesForCreate().contains(role);
    }

    public boolean isRoleAllowedToUpdate(String role) {
        return getRolesForUpdate().contains(role);
    }

    public boolean isRoleAllowedToDelete(String role) {
        return getRolesForDelete().contains(role);
    }

    public boolean isRoleAllowedToGet(String role) {

        System.out.println("********");
        System.out.println("Role: " + role);
        System.out.println("getRolesForGet(): " + getRolesForGet());
        System.out.println("********");

        return getRolesForGet().contains(role);
    }
}

