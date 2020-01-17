package com.angiii.learnplatform.service;

import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    public RespBean save(Teacher teacher) {
        teacher.setCreateTime(new Date());
        teacher.setUpdateTime(new Date());
        if (teacherMapper.insert(teacher) == 1) {
            return RespBean.ok("添加成功", teacher);
        }
        return RespBean.error("添加失败");
    }

    public RespBean all() {
        List<Teacher> teachers = teacherMapper.getAll();
        if (teachers != null) {
            return RespBean.ok("查找成功", teachers);
        }
        return RespBean.error("查找失败");
    }
}
