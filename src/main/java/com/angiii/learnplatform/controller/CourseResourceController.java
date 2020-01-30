package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.CourseResource;
import com.angiii.learnplatform.domain.entity.Faculty;
import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import com.angiii.learnplatform.service.CourseResourceService;
import com.angiii.learnplatform.service.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("courseResource")
@CrossOrigin
public class CourseResourceController {

    @Autowired
    CourseResourceService courseResourceService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean add(CourseResource courseResource) {
        return courseResourceService.save(courseResource);
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean getAll(@PathVariable(name = "type") ResourceTypeEnum resourceTypeEnum, PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        log.info("resourceTypeEnum{}", resourceTypeEnum);
        return courseResourceService.all(pageRequest, resourceTypeEnum);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean update(@PathVariable(name = "id") Long id, CourseResource courseResource) {
        return courseResourceService.update(id, courseResource);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean delete(@PathVariable(name = "id") Long id) {
        return courseResourceService.delete(id);
    }
}
