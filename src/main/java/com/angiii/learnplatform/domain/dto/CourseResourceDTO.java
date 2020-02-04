package com.angiii.learnplatform.domain.dto;

import com.angiii.learnplatform.domain.entity.CourseResource;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<CourseResourceDTO> convert(List<CourseResource> courseResources) {
        if (courseResources != null) {
            List<CourseResourceDTO> courseResourceDTOS = new ArrayList<>();
            for (CourseResource courseResource : courseResources) {
                CourseResourceDTO courseResourceDTO = CourseResourceDTO.builder().id(courseResource.getId()).name(courseResource.getName()).url(courseResource.getUrl()).
                        createTime(courseResource.getCreateTime()).updateTime(courseResource.getUpdateTime()).build();
                if (courseResource.getFaculty() != null) {
                    courseResourceDTO.setFacultyId(courseResource.getFaculty().getId());
                    courseResourceDTO.setFacultyName(courseResource.getFaculty().getName());
                }
                if (courseResource.getCourse() != null) {
                    courseResourceDTO.setCourseId(courseResource.getCourse().getId());
                    courseResourceDTO.setCourseName(courseResource.getCourse().getName());
                }
                if (courseResource.getTeacher() != null) {
                    courseResourceDTO.setTeacherId(courseResource.getTeacher().getId());
                    courseResourceDTO.setTeacherName(courseResource.getTeacher().getName());
                }
                courseResourceDTOS.add(courseResourceDTO);
            }
            return courseResourceDTOS;
        }
        throw new IllegalArgumentException("courseResources 参数有误");
    }
}
