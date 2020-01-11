package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.po.Faculty;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.service.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @PostMapping("/")
    public RespBean addFaculty(Faculty faculty) {
        return facultyService.save(faculty);
    }
}
