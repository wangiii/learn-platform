package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.CourseDao;
import com.angiii.learnplatform.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public List<Course> all() {
        List<Course> courses = courseDao.getAll();
        return courses;
    }

    public void save(Course course) {
        if (course != null && course.getName() != null) {
            course.setUpdateTime(new Date());
            course.setCreateTime(new Date());
            courseDao.insert(course);
        }
    }

    public Course find(Long id) {
        return courseDao.selectCourseById(id);
    }

    public void delete(Long id) {
        courseDao.delete(id);
    }

    public void update(Long id, Course course) {
        course.setId(id);
        course.setUpdateTime(new Date());
        courseDao.update(course);
    }
}
