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
package com.google.fhir.gateway.dtos;

public class DbUser {
    private String firstName;
    private String lastName;
    private String fhirPractitionerId;
    private String practitionerRole;
    private String id;
    private String idNumber;
    private String fullNames;
    private String phone;
    private String email;
    private String facility;
    private String facilityName;
    private String ward;
    private String wardName;
    private String subCounty;
    private String subCountyName;
    private String county;
    private String countyName;
    private String country;
    private String countryName;

    public DbUser(String firstName, String lastName, String fhirPractitionerId, String practitionerRole, String id,
                  String idNumber, String fullNames, String phone, String email, String facility, String facilityName,
                  String ward, String wardName, String subCounty, String subCountyName, String county,
                  String countyName, String country, String countryName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fhirPractitionerId = fhirPractitionerId;
        this.practitionerRole = practitionerRole;
        this.id = id;
        this.idNumber = idNumber;
        this.fullNames = fullNames;
        this.phone = phone;
        this.email = email;
        this.facility = facility;
        this.facilityName = facilityName;
        this.ward = ward;
        this.wardName = wardName;
        this.subCounty = subCounty;
        this.subCountyName = subCountyName;
        this.county = county;
        this.countyName = countyName;
        this.country = country;
        this.countryName = countryName;
    }

    // Getters and setters for all fields

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFhirPractitionerId() {
        return fhirPractitionerId;
    }

    public void setFhirPractitionerId(String fhirPractitionerId) {
        this.fhirPractitionerId = fhirPractitionerId;
    }

    public String getPractitionerRole() {
        return practitionerRole;
    }

    public void setPractitionerRole(String practitionerRole) {
        this.practitionerRole = practitionerRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }

    public String getSubCountyName() {
        return subCountyName;
    }

    public void setSubCountyName(String subCountyName) {
        this.subCountyName = subCountyName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
