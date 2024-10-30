package com.google.fhir.gateway;

import com.google.fhir.gateway.interfaces.ResourceValidator;
import com.google.fhir.gateway.validators.ImmunizationResourceValidator;
import com.google.fhir.gateway.validators.PatientResourceValidator;
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
        }else {
            resourceTypeData = resourceType;
        }

        if (Objects.equals(resourceTypeData, FHIRResourceTypesData.PATIENT.name())){
            return new PatientResourceValidator();
        }else if (Objects.equals(resourceTypeData, FHIRResourceTypesData.IMMUNIZATION.name())){
            return new ImmunizationResourceValidator();
        }else {
            return null; // or throw an exception here depending on your needs.
        }

    }
}
