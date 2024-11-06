package com.google.fhir.gateway.dtos;

public class Results {
    private int statusCode;
    private Object details;

    public Results(int statusCode, Object details) {
        this.statusCode = statusCode;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}
