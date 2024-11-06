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

public class DynamicResourceValidatorFactory {

    private final DynamicResourceRoleLoader roleLoader;

    public DynamicResourceValidatorFactory(DynamicResourceRoleLoader roleLoader) {
        this.roleLoader = roleLoader;
    }

    // Factory method to create a validator for any given resource
    public DynamicBaseResourceValidator createValidator(String resourceName) {
        return new DynamicBaseResourceValidator(roleLoader, resourceName);
    }
}

