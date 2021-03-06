package com.angiii.learnplatform.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private final String uuid;
    private final String token;
    private final String role;
    private final String userName;
}
