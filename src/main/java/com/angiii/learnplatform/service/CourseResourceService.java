package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.CourseResourceDTO;
import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.CourseResource;
import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.mapper.CourseResourceMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CourseResourceService {

    @Autowired
    CourseResourceMapper courseResourceMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public RespBean save(CourseResource courseResource) {
        if (courseResource != null && courseResource.getName() != null) {
            courseResource.setUpdateTime(new Date());
            courseResource.setCreateTime(new Date());

            Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
            courseResource.setTeacher(teacher);

            if (courseResourceMapper.insert(courseResource) == 1) {
                return RespBean.ok("添加成功", courseResource);
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean delete(Long id) {
        if (courseResourceMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
    }

    public RespBean update(Long id, CourseResource courseResource) {
        if (courseResource != null
                && courseResource.getName() != null) {
            courseResource.setId(id);
            courseResource.setUpdateTime(new Date());
            if (courseResourceMapper.update(courseResource) == 1) {
                CourseResource RealCourseResource = courseResourceMapper.selectCourseResourceById(courseResource.getId());
                return RespBean.ok("更新成功", RealCourseResource);
            }
        }
        throw new IllegalArgumentException("更新失败");
    }

    public RespBean all(PageRequest pageRequest, ResourceTypeEnum resourceTypeEnum) {

        Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
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
        Integer total = courseResourceMapper.getAllCount(resourceTypeEnum, teacher);
        Integer pages = total / pageSize + 1;
        PageResponse pageResponse = PageResponse.builder().
                pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();

        List<CourseResource> courseResources = courseResourceMapper.getPage(start, amount, resourceTypeEnum, teacher);

        if (courseResources != null) {
            List<CourseResourceDTO> courseResourceDTOS = new ArrayList<>();
            for (CourseResource courseResource : courseResources) {
                CourseResourceDTO courseResourceDTO = CourseResourceDTO.builder().id(courseResource.getId()).name(courseResource.getName()).url(courseResource.getUrl()).
                        createTime(courseResource.getCreateTime()).updateTime(courseResource.getUpdateTime()).build();
                if (courseResource.getFaculty() != null) {
                    courseResourceDTO.setFacultyId(courseResource.getFaculty().getId());
                    courseResourceDTO.setFacultyName(courseResource.getFaculty().getName());
                }
                if (courseResource.getCourse() != null) {
                    courseResourceDTO.setCourseId(courseResource.getCourse().getId());
                    courseResourceDTO.setCourseName(courseResource.getCourse().getName());
                }
                if (courseResource.getTeacher() != null) {
                    courseResourceDTO.setTeacherId(courseResource.getTeacher().getId());
                    courseResourceDTO.setTeacherName(courseResource.getTeacher().getName());
                }
                courseResourceDTOS.add(courseResourceDTO);
            }
            pageResponse.setList(courseResourceDTOS);
            pageResponse.setSize(courseResourceDTOS.size());
        }

        return RespBean.ok("查询成功", pageResponse);
    }
}
