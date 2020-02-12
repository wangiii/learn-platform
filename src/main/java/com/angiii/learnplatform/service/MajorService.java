package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.MajorDTO;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Major;
import com.angiii.learnplatform.mapper.MajorMapper;
import com.angiii.learnplatform.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MajorService {

    @Autowired
    private MajorMapper majorMapper;

    public RespBean save(Major major) {
        if (major != null && major.getName() != null) {
            major.setUpdateTime(new Date());
            major.setCreateTime(new Date());
            if (majorMapper.insert(major) == 1) {
                return RespBean.ok("添加成功", major);
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean delete(Long id) {
        if (majorMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
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
        throw new IllegalArgumentException("更新失败");
    }

    public RespBean all(PageRequest pageRequest) {
        Integer total = majorMapper.getAllCount();
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();
        List<Major> majors = majorMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize());
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

    public RespBean getMajorsDtoByFaculty(Long facultyId) {
        return RespBean.ok("查询成功", majorMapper.selectMajorsDtoByFaculty(facultyId));
    }

    public RespBean getMajorsByFacultyId(Long facultyId) {
        if (facultyId == null) {
            return RespBean.ok("查询成功", majorMapper.selectMajors());
        }
        return RespBean.ok("查询成功", majorMapper.selectMajorsByFacultyId(facultyId));
    }
}
