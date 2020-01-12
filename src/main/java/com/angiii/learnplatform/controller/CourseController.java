package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.po.Course;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public RespBean getAllCourse() {
        return courseService.all();
    }

    @PostMapping("/")
    public RespBean addCourse(Course course) {
        return courseService.save(course);
    }

    @GetMapping("/{id}")
    public RespBean findCourseById(@PathVariable(name = "id") Long id) {
        return courseService.find(id);
    }

    @PutMapping("/{id}")
    public RespBean updateCourse(@PathVariable(name = "id") Long id, Course course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    public RespBean deleteCourse(@PathVariable(name = "id") Long id) {
        return courseService.delete(id);
    }
}
