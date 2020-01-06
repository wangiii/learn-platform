package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.CourseDao;
import com.angiii.learnplatform.model.Course;
import com.angiii.learnplatform.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public RespBean all() {
        List<Course> courses = courseDao.getAll();
        return RespBean.ok("查询成功", courses);
    }

    public RespBean save(Course course) {
        if (course != null && course.getName() != null) {
            course.setUpdateTime(new Date());
            course.setCreateTime(new Date());
            if (courseDao.insert(course) == 1) {
                return RespBean.ok("添加成功", course);
            }
        }
        return RespBean.error("添加失败");
    }

    public RespBean find(Long id) {
        Course course = courseDao.selectCourseById(id);
        if (course != null) {
            return RespBean.ok("查询成功", course);
        }
        return RespBean.error("查询失败");
    }

    public RespBean delete(Long id) {
        if (courseDao.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    public RespBean update(Long id, Course course) {
        if (course != null && course.getName() != null) {
            course.setId(id);
            course.setUpdateTime(new Date());
            if (courseDao.update(course) == 1) {
                Course RealCourse = courseDao.selectCourseById(course.getId());
                return RespBean.ok("更新成功", RealCourse);
            }
        }
        return RespBean.error("更新失败");
    }
}
