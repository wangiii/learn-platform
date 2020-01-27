package com.angiii.learnplatform.service;

import com.angiii.learnplatform.mapper.FacultyMapper;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.entity.Faculty;
import com.angiii.learnplatform.domain.dto.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FacultyService {

    @Autowired
    FacultyMapper facultyMapper;

    public RespBean save(Faculty faculty) {
        if (faculty != null && faculty.getName() != null) {
            faculty.setUpdateTime(new Date());
            faculty.setCreateTime(new Date());
            if (facultyMapper.insert(faculty) == 1) {
                return RespBean.ok("添加成功", faculty);
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean delete(Long id) {
        if (facultyMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
    }

    public RespBean update(Long id, Faculty faculty) {
        if (faculty != null
                && faculty.getName() != null) {
            faculty.setId(id);
            faculty.setUpdateTime(new Date());
            if (facultyMapper.update(faculty) == 1) {
                Faculty RealFaculty = facultyMapper.selectFacultyById(faculty.getId());
                return RespBean.ok("更新成功", RealFaculty);
            }
        }
        throw new IllegalArgumentException("更新失败");
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
        Integer total = facultyMapper.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Faculty> faculties = facultyMapper.getPage(start, amount);
        pageResponse.setList(faculties);
        pageResponse.setSize(faculties.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean allFacultyDTO() {
        return RespBean.ok("查询成功", facultyMapper.getFacultyDTO());
    }
}
