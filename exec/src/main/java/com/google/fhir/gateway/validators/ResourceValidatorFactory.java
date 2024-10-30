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
            case "Parameters":
                return new ParametersResourceValidator();
            case "SupplyDelivery":
                return new SupplyDeliveryResourceValidator();
            case "SupplyRequest":
                return new SupplyRequestResourceValidator();
            case "Observation":
                return new ObservationResourceValidator();
            case "AdverseEvent":
                return new AdverseEventResourceValidator();
            case "Appointment":
                return new AppointmentResourceValidator();
            case "CarePlan":
                return new CarePlanResourceValidator();
            case "Location":
                return new LocationResourceValidator();
            case "Practitioner":
                return new PractionerResourceValidator();
            default:
                throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }

    }
}
