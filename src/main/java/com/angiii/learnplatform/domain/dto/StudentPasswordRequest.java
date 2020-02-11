package com.angiii.learnplatform.domain.dto;

import lombok.*;

@Data
@ToString()
@AllArgsConstructor
@NoArgsConstructor
public class StudentPasswordRequest {

    private String password;
    private String oldPassword;
}
