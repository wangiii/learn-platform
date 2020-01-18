package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.MajorDTO;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Faculty;
import com.angiii.learnplatform.domain.entity.Major;
import com.angiii.learnplatform.mapper.FacultyMapper;
import com.angiii.learnplatform.mapper.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MajorService {

    @Autowired
    MajorMapper majorMapper;

    @Autowired
    FacultyMapper facultyMapper;

    public RespBean save(Major major) {
        if (major != null && major.getName() != null) {
            major.setUpdateTime(new Date());
            major.setCreateTime(new Date());
            if (majorMapper.insert(major) == 1) {
                return RespBean.ok("添加成功", major);
            }
        }
        return RespBean.error("添加失败");
    }

    public RespBean delete(Long id) {
        if (majorMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    public RespBean update(Long id, Major major) {
        if (major != null
                && major.getName() != null
                && major.getFaculty() != null) {
            major.setId(id);
            major.setUpdateTime(new Date());
            if (majorMapper.update(major) == 1) {
                Major RealFaculty = majorMapper.selectMajorById(major.getId());
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
        Integer total = majorMapper.getAllCount();
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
        List<Major> majors = majorMapper.getPage(start, amount);
        if (majors != null) {
            List<MajorDTO> majorDTOs = new ArrayList<>();
            for (Major major: majors) {
                MajorDTO majorDTO = MajorDTO.builder().id(major.getId()).name(major.getName()).
                        createTime(major.getCreateTime()).updateTime(major.getUpdateTime()).build();
                if (major.getFaculty() != null) {
                    majorDTO.setFacultyId(major.getFaculty().getId());
                    majorDTO.setFacultyName(major.getFaculty().getName());
                }
                majorDTOs.add(majorDTO);
            }
            pageResponse.setList(majorDTOs);
            pageResponse.setSize(majorDTOs.size());
        }

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean getAllMajors() {
        return RespBean.ok("查询成功", majorMapper.selectAllMajors());
    }
}
