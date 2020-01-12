package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.po.Teacher;
import com.angiii.learnplatform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("/")
    public RespBean all() {
        return teacherService.all();
    }

    @PostMapping("/")
    public RespBean save(Teacher teacher) {
        return teacherService.save(teacher);
    }
}
