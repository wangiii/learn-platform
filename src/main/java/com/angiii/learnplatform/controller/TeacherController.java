package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher")
@CrossOrigin
@Slf4j
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getAllTeacher(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return teacherService.all(pageRequest);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean addTeacher(Teacher teacher) {
        return teacherService.save(teacher);
    }

    @PutMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean updateTeacher(@PathVariable(name = "phone") String phone, Teacher teacher) {
        return teacherService.update(phone, teacher);
    }

    @GetMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean findTeacherByPhone(@PathVariable(name = "phone") String phone) {
        return teacherService.find(phone);
    }

    @DeleteMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean deleteTeacher(@PathVariable(name = "phone") String phone) {
        return teacherService.delete(phone);
    }
}
