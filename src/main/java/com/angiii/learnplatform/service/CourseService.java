package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.CourseDao;
import com.angiii.learnplatform.dto.PageRequest;
import com.angiii.learnplatform.po.Course;
import com.angiii.learnplatform.dto.RespBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public RespBean all(PageRequest pageRequest) {
        int pageNum = 1;
        int pageSize = 2;
        if (pageRequest != null) {
            pageNum = pageRequest.getPageNum();
            pageSize = pageRequest.getPageSize();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Course> courses = courseDao.getAll();
        PageInfo<Course> pageInfo = new PageInfo<>(courses);

        return RespBean.ok("查询成功", pageInfo);
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
