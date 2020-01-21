package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Student;
import com.angiii.learnplatform.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

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
        Integer total = studentMapper.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Student> students = studentMapper.getPage(start, amount);
        pageResponse.setList(students);
        pageResponse.setSize(students.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean save(Student student) {
        if (student != null && student.getName() != null && student.getPhone() != null) {
            student.setUpdateTime(new Date());
            student.setCreateTime(new Date());
            if (studentMapper.insert(student) == 1) {
                return RespBean.ok("添加成功", student);
            }
        }
        return RespBean.error("添加失败");
    }

    public RespBean find(String phone) {
        Student student = studentMapper.selectStudentByPhone(phone);
        if (student != null) {
            return RespBean.ok("查询成功", student);
        }
        return RespBean.error("查询失败");
    }

    public RespBean update(String phone, Student student) {
        if (student != null
                && student.getName() != null
                && student.getPassword() != null) {
            student.setPhone(phone);
            student.setUpdateTime(new Date());
            if (studentMapper.update(student) == 1) {
                Student RealStudent = studentMapper.selectStudentByPhone(student.getPhone());
                return RespBean.ok("更新成功", RealStudent);
            }
        }
        return RespBean.error("更新失败");
    }

    public RespBean delete(String phone) {
        if (studentMapper.delete(phone) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
