package com.angiii.learnplatform.domain.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author JasonVV
 * @date 2020-02-22 22:40
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacultyPieRowDTO implements Serializable {
    private String 院系;
    private Integer 在线课程数量;
}
