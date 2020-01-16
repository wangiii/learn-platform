package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.FacultyDao;
import com.angiii.learnplatform.dto.PageRequest;
import com.angiii.learnplatform.dto.PageResponse;
import com.angiii.learnplatform.po.Faculty;
import com.angiii.learnplatform.dto.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FacultyService {

    @Autowired
    FacultyDao facultyDao;

    public RespBean save(Faculty faculty) {
        if (faculty != null && faculty.getName() != null) {
            faculty.setUpdateTime(new Date());
            faculty.setCreateTime(new Date());
            if (facultyDao.insert(faculty) == 1) {
                return RespBean.ok("添加成功", faculty);
            }
        }
        return RespBean.error("添加失败");
    }

    public RespBean delete(Long id) {
        if (facultyDao.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    public RespBean update(Long id, Faculty faculty) {
        if (faculty != null
                && faculty.getName() != null) {
            faculty.setId(id);
            faculty.setUpdateTime(new Date());
            if (facultyDao.update(faculty) == 1) {
                Faculty RealFaculty = facultyDao.selectFacultyById(faculty.getId());
                return RespBean.ok("更新成功", RealFaculty);
            }
        }
        return RespBean.error("更新失败");
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
        Integer total = facultyDao.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Faculty> faculties = facultyDao.getPage(start, amount);
        pageResponse.setList(faculties);
        pageResponse.setSize(faculties.size());

        return RespBean.ok("查询成功", pageResponse);
    }
}
