package com.google.fhir.gateway.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String status;
    private DbUser user;

    public UserResponse(String status, DbUser user) {
        this.status = status;
        this.user = user;
    }

}
