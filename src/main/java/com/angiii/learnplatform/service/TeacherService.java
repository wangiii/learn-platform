package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.TeacherDTO;
import com.angiii.learnplatform.mapper.TeacherCourseMapper;
import com.angiii.learnplatform.mapper.TeacherMajorMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherMajorMapper teacherMajorMapper;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    public RespBean save(Teacher teacher) {
        teacher.setCreateTime(new Date());
        teacher.setUpdateTime(new Date());
        if (teacherMapper.insert(teacher) == 1) {
            return RespBean.ok("添加成功", teacher);
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean all(PageRequest pageRequest) {
        Integer total = teacherMapper.getAllCount();
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();
        List<Teacher> teachers = teacherMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize());
        TeacherDTO dto = new TeacherDTO();
        List<TeacherDTO> teacherDTOS = dto.convert(teachers);
        pageResponse.setList(teacherDTOS);
        pageResponse.setSize(teacherDTOS.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean find(String phone) {
        Teacher teacher = teacherMapper.selectTeacherByPhone(phone);
        if (teacher != null) {
            return RespBean.ok("查询成功", teacher);
        }
        return RespBean.error("查询失败");
    }

    public RespBean update(String phone, Teacher teacher) {
        if (teacher != null
                && teacher.getName() != null
                && teacher.getFaculty() != null) {
            teacher.setPhone(phone);
            teacher.setUpdateTime(new Date());
            if (teacherMapper.update(teacher) == 1) {
                Teacher RealTeacher = teacherMapper.selectTeacherByPhone(teacher.getPhone());
                return RespBean.ok("更新成功", RealTeacher);
            }
        }
        throw new IllegalArgumentException("更新失败");
    }

    public RespBean delete(String phone) {
        if (teacherMapper.delete(phone) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
    }

    public void updateTeacherMajors(long teacherId, String[] MajorIds) {
        teacherMajorMapper.delete(teacherId);
        for (String majorId : MajorIds) {
            if (!majorId.equals("[object Object]")) {
                log.info("majorId:{}",majorId);
                teacherMajorMapper.insert(teacherId, majorId);
            }
        }
    }

    public void updateTeacherCourses(long teacherId, String[] courseIds) {
        teacherCourseMapper.delete(teacherId);
        for (String courseId : courseIds) {
            if (!courseId.equals("[object Object]")) {
                log.info("courseId:{}",courseId);
                teacherCourseMapper.insert(teacherId, courseId);
            }
        }
    }

    public RespBean search(PageRequest pageRequest, String phone) {
        Integer total = teacherMapper.getSearchCount(phone);
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();
        List<Teacher> teachers = teacherMapper.getSearch(pageUtil.getStart(), pageUtil.getPageSize(), phone);
        TeacherDTO dto = new TeacherDTO();
        List<TeacherDTO> teacherDTOS = dto.convert(teachers);
        pageResponse.setList(teacherDTOS);
        pageResponse.setSize(teacherDTOS.size());

        return RespBean.ok("查询成功", pageResponse);
    }
}
