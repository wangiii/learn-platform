package com.angiii.learnplatform.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@ToString()
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResourceDTO {

    private Long id;
    private String name;
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long facultyId;
    private String facultyName;

    private Long courseId;
    private String courseName;

    private Long teacherId;
    private String teacherName;
}
