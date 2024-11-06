package com.google.fhir.gateway.dtos;

public class DbResults {
    private Object details;

    public DbResults(Object details) {
        this.details = details;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}
