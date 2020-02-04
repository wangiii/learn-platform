package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.mapper.CourseMapper;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.entity.Course;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.mapper.MajorCourseMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.util.AuthUtil;
import com.angiii.learnplatform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MajorCourseMapper majorCourseMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public RespBean all(PageRequest pageRequest) {
        Integer total = courseMapper.getAllCount();
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        List<Course> courses = courseMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize());
        PageResponse pageResponse = pageUtil.getPageResponse();
        pageResponse.setList(courses);
        pageResponse.setSize(courses.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean allDto() {
        Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
        return RespBean.ok("查询成功", courseMapper.getAllDto(teacher));
    }

    public RespBean allDtoForAdmin() {
        return RespBean.ok("查询成功", courseMapper.getAllDtoForAdmin());
    }

    public RespBean save(Course course) {
        if (course != null && course.getName() != null) {
            course.setUpdateTime(new Date());
            course.setCreateTime(new Date());
            if (courseMapper.insert(course) == 1) {
                return RespBean.ok("添加成功", course);
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean find(Long id) {
        Course course = courseMapper.selectCourseById(id);
        if (course != null) {
            return RespBean.ok("查询成功", course);
        }
        return RespBean.error("查询失败");
    }

    public RespBean delete(Long id) {
        if (courseMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
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
            if (courseMapper.update(course) == 1) {
                Course RealCourse = courseMapper.selectCourseById(course.getId());
                return RespBean.ok("更新成功", RealCourse);
            }
        }
        throw new IllegalArgumentException("更新失败");
    }

    public void updateCourseMajors(long courseId, String[] MajorIds) {
        majorCourseMapper.delete(courseId);
        for (String majorId : MajorIds) {
            if (!majorId.equals("[object Object]")) {
                majorCourseMapper.insert(courseId, majorId);
            }
        }
    }

    public RespBean getByMajorId(Long majorId, PageRequest pageRequest) {
        Integer total = courseMapper.getCountByMajor(majorId);
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        List<Course> courses = courseMapper.selectCoursesByMajorId(majorId, pageUtil.getStart(), pageUtil.getPageSize());
        PageResponse pageResponse = pageUtil.getPageResponse();
        pageResponse.setList(courses);
        pageResponse.setSize(courses.size());

        return RespBean.ok("查询成功", pageResponse);
    }
}
