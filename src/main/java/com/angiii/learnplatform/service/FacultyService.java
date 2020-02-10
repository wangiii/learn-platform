package com.angiii.learnplatform.service;

import com.angiii.learnplatform.mapper.FacultyMapper;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.entity.Faculty;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.util.PageUtil;
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
        Integer total = facultyMapper.getAllCount();
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        List<Faculty> faculties = facultyMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize());
        PageResponse pageResponse = pageUtil.getPageResponse();
        pageResponse.setList(faculties);
        pageResponse.setSize(faculties.size());

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean allFacultyDTO() {
        return RespBean.ok("查询成功", facultyMapper.getFacultyDTO());
    }

    public RespBean allFacultyResponse() {
        return RespBean.ok("查询成功", facultyMapper.getFacultyResponse());
    }

    public List<Faculty> getAllWithMajor() {
        return facultyMapper.getAllWithMajor();
    }
}
