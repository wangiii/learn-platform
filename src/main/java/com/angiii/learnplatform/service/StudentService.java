package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.dto.StudentDTO;
import com.angiii.learnplatform.domain.entity.Student;
import com.angiii.learnplatform.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

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
        if (students != null) {
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (Student student: students) {
                StudentDTO studentDTO = StudentDTO.builder().id(student.getId()).name(student.getName()).phone(student.getPhone()).
                        createTime(student.getCreateTime()).updateTime(student.getUpdateTime()).build();
                if (student.getFaculty() != null) {
                    studentDTO.setFacultyId(student.getFaculty().getId());
                    studentDTO.setFacultyName(student.getFaculty().getName());
                }
                if (student.getMajor() != null) {
                    studentDTO.setMajorId(student.getMajor().getId());
                    studentDTO.setMajorName(student.getMajor().getName());
                }
                studentDTOS.add(studentDTO);
            }
            pageResponse.setList(studentDTOS);
            pageResponse.setSize(studentDTOS.size());
        }

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean search(PageRequest pageRequest, String phone) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(phone);
        if (!isNum.matches()) {
            throw new IllegalArgumentException("手机号有误");
        }
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
        Integer total = studentMapper.getSearchCount(phone);
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Student> students = studentMapper.getSearch(start, amount, phone);
        if (students != null) {
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (Student student: students) {
                StudentDTO studentDTO = StudentDTO.builder().id(student.getId()).name(student.getName()).phone(student.getPhone()).
                        createTime(student.getCreateTime()).updateTime(student.getUpdateTime()).build();
                if (student.getFaculty() != null) {
                    studentDTO.setFacultyId(student.getFaculty().getId());
                    studentDTO.setFacultyName(student.getFaculty().getName());
                }
                if (student.getMajor() != null) {
                    studentDTO.setMajorId(student.getMajor().getId());
                    studentDTO.setMajorName(student.getMajor().getName());
                }
                studentDTOS.add(studentDTO);
            }
            pageResponse.setList(studentDTOS);
            pageResponse.setSize(studentDTOS.size());
        }

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
