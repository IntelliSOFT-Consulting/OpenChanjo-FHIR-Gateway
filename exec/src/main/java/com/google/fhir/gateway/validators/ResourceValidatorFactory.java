package com.google.fhir.gateway.validators;

import com.google.fhir.gateway.FHIRResourceTypesData;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import com.google.fhir.gateway.resource_validators.ImmunizationResourceValidator;
import com.google.fhir.gateway.resource_validators.PatientResourceValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ResourceValidatorFactory {
    public ResourceValidator getValidator(String resourceType) {

        String resourceTypeData = "";
        if (Objects.equals(resourceType, "Patient")) {
             resourceTypeData = FHIRResourceTypesData.PATIENT.name();
        } else if (Objects.equals(resourceType, "Immunization")) {
            resourceTypeData = FHIRResourceTypesData.IMMUNIZATION.name();
        } else if (Objects.equals(resourceType, "ImmunizationRecommendation")) {
            resourceTypeData = FHIRResourceTypesData.IMMUNIZATION_RECOMMENDATION.name();
        }else {
            resourceTypeData = resourceType;
        }

        if (Objects.equals(resourceTypeData, FHIRResourceTypesData.PATIENT.name())){
            return new PatientResourceValidator();
        }else if (Objects.equals(resourceTypeData, FHIRResourceTypesData.IMMUNIZATION.name())){
            return new ImmunizationResourceValidator();
        }else if (Objects.equals(resourceTypeData, FHIRResourceTypesData.IMMUNIZATION_RECOMMENDATION.name())){
            return new ImmunizationResourceValidator();
        }else {
            return null; // or throw an exception here depending on your needs.
        }

    }
}
