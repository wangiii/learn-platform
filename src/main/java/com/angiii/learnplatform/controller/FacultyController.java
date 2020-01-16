package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.dto.PageRequest;
import com.angiii.learnplatform.po.Faculty;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.service.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("faculty")
@CrossOrigin
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean addFaculty(Faculty faculty) {
        return facultyService.save(faculty);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getAllFaculty(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return facultyService.all(pageRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean updateFaculty(@PathVariable(name = "id") Long id, Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean deleteFaculty(@PathVariable(name = "id") Long id) {
        return facultyService.delete(id);
    }
}
