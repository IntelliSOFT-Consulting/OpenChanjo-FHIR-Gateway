package com.google.fhir.gateway.validators;

import com.google.fhir.gateway.FHIRResourceTypesData;
import com.google.fhir.gateway.interfaces.ResourceValidator;
import com.google.fhir.gateway.resource_validators.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ResourceValidatorFactory {
    public ResourceValidator getValidator(String resourceType) {

        // Use switch or if-else to directly return the appropriate validator
        switch (resourceType) {
            case "Patient":
                return new PatientResourceValidator();
            case "Immunization":
            case "ImmunizationRecommendation":
                return new ImmunizationResourceValidator();
            case "Basic":
                return new BasicResourceValidator();
            case "AuditEvent":
                return new AuditEventResourceValidator();
            case "Library":
                return new LibraryResourceValidator();
            default:
                throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }

    }
}
