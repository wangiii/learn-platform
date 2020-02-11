package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.dto.StudentDTO;
import com.angiii.learnplatform.domain.entity.Student;
import com.angiii.learnplatform.mapper.StudentMapper;
import com.angiii.learnplatform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RespBean all(PageRequest pageRequest) {
        Integer total = studentMapper.getAllCount();
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();
        List<Student> students = studentMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize());
        StudentDTO dto = new StudentDTO();
        List<StudentDTO> studentDTOS = dto.convert(students);
        pageResponse.setList(studentDTOS);
        pageResponse.setSize(studentDTOS.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean search(PageRequest pageRequest, String phone) {
        Integer total = studentMapper.getSearchCount(phone);
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();
        List<Student> students = studentMapper.getSearch(pageUtil.getStart(), pageUtil.getPageSize(), phone);
        StudentDTO dto = new StudentDTO();
        List<StudentDTO> studentDTOS = dto.convert(students);
        pageResponse.setList(studentDTOS);
        pageResponse.setSize(studentDTOS.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean save(Student student) {
        if (student != null && student.getName() != null && student.getPhone() != null) {
            student.setUpdateTime(new Date());
            student.setCreateTime(new Date());
            student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
            if (studentMapper.insert(student) == 1) {
                return RespBean.ok("添加成功", student);
            }
        }
        throw new IllegalArgumentException("添加失败");
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
        throw new IllegalArgumentException("更新失败");
    }

    public RespBean delete(String phone) {
        if (studentMapper.delete(phone) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
    }
}
