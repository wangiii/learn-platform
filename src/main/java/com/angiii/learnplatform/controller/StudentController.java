package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.dto.StudentPasswordRequest;
import com.angiii.learnplatform.domain.entity.Student;
import com.angiii.learnplatform.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
@Slf4j
@CrossOrigin
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getAllStudent(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return studentService.all(pageRequest);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getSearchTeacher(PageRequest pageRequest, String phone) {
        log.info("searchPhone{}", phone);
        return studentService.search(pageRequest, phone);
    }

    @DeleteMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean deleteStudent(@PathVariable(name = "phone") String phone) {
        return studentService.delete(phone);
    }

    @PostMapping("/")
    public RespBean addStudent(Student student) {
        log.info("student:{}", student);
        return studentService.save(student);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    public RespBean updateStudent(StudentPasswordRequest studentPasswordRequest) {
        return studentService.update(studentPasswordRequest);
    }

    @GetMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean findStudentByPhone(@PathVariable(name = "phone") String phone) {
        return studentService.find(phone);
    }
}
