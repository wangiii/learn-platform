package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.*;
import com.angiii.learnplatform.domain.entity.CourseResource;
import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.mapper.CourseResourceMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.util.AliyunOssUtil;
import com.angiii.learnplatform.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

    @Autowired
    AliyunOssUtil aliyunOssUtil;

    public RespBean save(CourseResource courseResource) {
        if (courseResource != null
                && courseResource.getName() != null
                && courseResource.getCourse() != null
                && courseResource.getUrl() != null
                && courseResource.getType() != null) {
            Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
            courseResource.setFaculty(teacher.getFaculty());
            courseResource.setTeacher(teacher);
            courseResource.setUpdateTime(new Date());
            courseResource.setCreateTime(new Date());

            if (courseResourceMapper.insert(courseResource) == 1) {
                return RespBean.ok("添加成功", courseResource);
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean saveRes(CourseResourceRequest courseResourceRequest) {
        if (courseResourceRequest.getFile() != null && courseResourceRequest.getType() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String now = sdf.format(new Date());
            String originalFilename = courseResourceRequest.getFile().getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            int random = (int)((Math.random()*9+1)*1000);
            String fileName = courseResourceRequest.getType() + "/" + now + random + "." + suffix;

            try {
                InputStream inputStream = courseResourceRequest.getFile().getInputStream();
                aliyunOssUtil.upload(inputStream, fileName);
            } catch (IOException e) {
                return RespBean.error("文件上传失败");
            }

            String url = "http://learn-platform-jason.oss-cn-shenzhen.aliyuncs.com/" + fileName;
            return RespBean.ok("添加成功", url);
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
