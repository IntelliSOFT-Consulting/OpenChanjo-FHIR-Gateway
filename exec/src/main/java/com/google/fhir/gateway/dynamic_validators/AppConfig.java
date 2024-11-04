package com.google.fhir.gateway.dynamic_validators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public DynamicResourceRoleLoader dynamicResourceRoleLoader() throws IOException {
        return new DynamicResourceRoleLoader();
    }

    @Bean
    public DynamicResourceValidatorFactory dynamicResourceValidatorFactory(DynamicResourceRoleLoader roleLoader) {
        return new DynamicResourceValidatorFactory(roleLoader);
    }

    @Bean
    public ValidatorService validatorService(DynamicResourceValidatorFactory validatorFactory) {
        return new ValidatorService(validatorFactory);
    }
}

