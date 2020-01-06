package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.model.Course;
import com.angiii.learnplatform.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public List<Course> getAllCourse() {
        List<Course> courses = courseService.all();
        log.info("courses:{}",courses);
        return courses;
    }

    @PostMapping("/")
    public void addCourse(Course course) {
        log.info("course{}", course);
        if (course != null && course.getName() != null) {
            courseService.save(course);
        }
    }

    @GetMapping("/{id}")
    public Course findCourseById(@PathVariable(name = "id") Long id) {
        return courseService.find(id);
    }

    @PutMapping("/{id}")
    public void updateCourse(@PathVariable(name = "id") Long id, Course course) {
        if (course != null && course.getName() != null) {
            courseService.update(id, course);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable(name = "id") Long id) {
        courseService.delete(id);
    }
}
