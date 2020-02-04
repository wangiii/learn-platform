package com.angiii.learnplatform.domain.dto;

import com.angiii.learnplatform.domain.entity.Student;
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
public class StudentDTO {

    private Long id;
    private String name;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long facultyId;
    private String facultyName;

    private Long majorId;
    private String majorName;

    public List<StudentDTO> convert(List<Student> students) {
        if (students != null) {
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (Student student: students) {
                StudentDTO studentDTO = StudentDTO.builder().id(student.getId()).name(student.getName()).phone(student.getPhone()).
                        createTime(student.getCreateTime()).updateTime(student.getUpdateTime()).build();
                if (student.getFaculty() != null) {
                    studentDTO.setFacultyId(student.getFaculty().getId());
                    studentDTO.setFacultyName(student.getFaculty().getName());
                }
                if (student.getMajor() != null) {
                    studentDTO.setMajorId(student.getMajor().getId());
                    studentDTO.setMajorName(student.getMajor().getName());
                }
                studentDTOS.add(studentDTO);
            }
            return studentDTOS;
        }
        throw new IllegalArgumentException("students 为 null");
    }
}
