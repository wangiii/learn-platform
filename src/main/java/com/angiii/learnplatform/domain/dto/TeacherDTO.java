package com.angiii.learnplatform.domain.dto;

import com.angiii.learnplatform.domain.entity.Major;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@ToString()
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO {

    private Long id;
    private String name;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long facultyId;
    private String facultyName;

    private List<Major> majors;
}
