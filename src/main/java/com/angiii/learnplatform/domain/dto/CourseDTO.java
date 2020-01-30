package com.angiii.learnplatform.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString()
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private String value;
    private String label;
}
