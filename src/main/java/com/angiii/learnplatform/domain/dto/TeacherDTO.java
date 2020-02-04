package com.angiii.learnplatform.domain.dto;

import com.angiii.learnplatform.domain.entity.Course;
import com.angiii.learnplatform.domain.entity.Major;
import com.angiii.learnplatform.domain.entity.Teacher;
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
    private List<Course> courses;

    public List<TeacherDTO> convert(List<Teacher> teachers) {
        if (teachers != null) {
            List<TeacherDTO> teacherDTOS = new ArrayList<>();
            for (Teacher teacher : teachers) {
                TeacherDTO teacherDTO = TeacherDTO.builder().id(teacher.getId()).name(teacher.getName()).phone(teacher.getPhone()).
                        createTime(teacher.getCreateTime()).updateTime(teacher.getUpdateTime()).build();
                if (teacher.getFaculty() != null) {
                    teacherDTO.setFacultyId(teacher.getFaculty().getId());
                    teacherDTO.setFacultyName(teacher.getFaculty().getName());
                }
                if (teacher.getMajors() != null) {
                    teacherDTO.setMajors(teacher.getMajors());
                }
                if (teacher.getCourses() != null) {
                    teacherDTO.setCourses(teacher.getCourses());
                }
                teacherDTOS.add(teacherDTO);
            }
            return teacherDTOS;
        }
        throw new IllegalArgumentException("teachers 为 null");
    }
}
