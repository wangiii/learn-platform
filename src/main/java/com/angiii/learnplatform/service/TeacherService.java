package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.TeacherDTO;
import com.angiii.learnplatform.domain.entity.Major;
import com.angiii.learnplatform.mapper.TeacherMajorMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherMajorMapper teacherMajorMapper;

    public RespBean save(Teacher teacher) {
        teacher.setCreateTime(new Date());
        teacher.setUpdateTime(new Date());
        if (teacherMapper.insert(teacher) == 1) {
            return RespBean.ok("添加成功", teacher);
        }
        return RespBean.error("添加失败");
    }

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
        Integer total = teacherMapper.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Teacher> teachers = teacherMapper.getPage(start, amount);
        if (teachers != null) {
            List<TeacherDTO> teacherDTOS = new ArrayList<>();
            for (Teacher teacher : teachers) {
                TeacherDTO teacherDTO = TeacherDTO.builder().id(teacher.getId()).name(teacher.getName()).phone(teacher.getPhone()).
                        createTime(teacher.getCreateTime()).updateTime(teacher.getUpdateTime()).build();
                if (teacher.getFaculty() != null) {
                    teacherDTO.setFacultyId(teacher.getFaculty().getId());
                    teacherDTO.setFacultyName(teacher.getFaculty().getName());
                }
                if (teacher.getMajors() != null) {
                    teacherDTO.setMajors(teacher.getMajors());
                }
                teacherDTOS.add(teacherDTO);
            }
            pageResponse.setList(teacherDTOS);
            pageResponse.setSize(teacherDTOS.size());
        }

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
        return RespBean.error("更新失败");
    }

    public RespBean delete(String phone) {
        if (teacherMapper.delete(phone) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    public void updateTeacherMajors(long teacherId, String[] MajorIds) {
        teacherMajorMapper.delete(teacherId);
        for (String majorId : MajorIds) {
            if (!majorId.equals("[object Object]")) {
                teacherMajorMapper.insert(teacherId, majorId);
            }
        }
    }
}
