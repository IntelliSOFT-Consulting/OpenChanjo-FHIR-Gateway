package com.google.fhir.gateway

data class Results(
    val statusCode: Int,
    val details: Any?
)
data class DbResults(
    val details: Any?
)
data class UserResponse(
    val status: String,
    val user: DbUser
)

data class DbUser(
    val firstName: String,
    val lastName: String,
    val fhirPractitionerId: String,
    val practitionerRole: String,
    val id: String,
    val idNumber: String,
    val fullNames: String,
    val phone: String,
    val email: String,
    val facility: String,
    val facilityName: String,
    val ward: String,
    val wardName: String,
    val subCounty: String,
    val subCountyName: String,
    val county: String,
    val countyName: String,
    val country: String,
    val countryName: String
)

enum class FHIRResourceTypesData {
    PATIENT,
    IMMUNIZATION,
    IMMUNIZATION_RECOMMENDATION,
    MEDICATION,
    ORDER
}
