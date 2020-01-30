package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.entity.Course;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getAllCourse(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return courseService.all(pageRequest);
    }

    @GetMapping("/dto")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean getAllCourse() {
        return courseService.allDto();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean addCourse(Course course) {
        return courseService.save(course);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean findCourseById(@PathVariable(name = "id") Long id) {
        return courseService.find(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean updateCourse(@PathVariable(name = "id") Long id, @RequestParam(value = "majorIds") String majorIds, Course course) {
        if (!majorIds.equals("")) {
            String[] ids = majorIds.split(",");
            courseService.updateCourseMajors(id, ids);
        }
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean deleteCourse(@PathVariable(name = "id") Long id) {
        return courseService.delete(id);
    }
}
