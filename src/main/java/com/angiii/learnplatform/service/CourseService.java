package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.CourseDao;
import com.angiii.learnplatform.dto.PageRequest;
import com.angiii.learnplatform.dto.PageResponse;
import com.angiii.learnplatform.po.Course;
import com.angiii.learnplatform.dto.RespBean;
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
        int pageSize = 5;
        if (pageRequest != null
                && pageRequest.getPageSize() > 0
                && pageRequest.getPageNum() > 0) {
            pageNum = pageRequest.getPageNum();
            pageSize = pageRequest.getPageSize();
        }

        Integer start = (pageNum - 1) * pageSize;
        Integer amount = pageSize;
        Integer total = courseDao.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Course> courses = courseDao.getPage(start, amount);
        pageResponse.setList(courses);
        pageResponse.setSize(courses.size());

        return RespBean.ok("查询成功", pageResponse);
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
        if (course != null
                && course.getName() != null
                && course.getClassHour() != null
                && course.getCover() != null
                && course.getSemester() != null
                && course.getCredit() != null) {
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
