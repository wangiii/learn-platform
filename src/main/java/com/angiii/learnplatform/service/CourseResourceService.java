package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.*;
import com.angiii.learnplatform.domain.entity.CourseResource;
import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.mapper.CourseResourceMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.util.AliyunOssUtil;
import com.angiii.learnplatform.util.AuthUtil;
import com.angiii.learnplatform.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
        Integer total = courseResourceMapper.getAllCount(resourceTypeEnum, teacher);
        PageResponse pageResponse = getPageResponseForAll(pageRequest, total, resourceTypeEnum, teacher);

        return RespBean.ok("查询成功", pageResponse);
    }

    public RespBean getByCourse(PageRequest pageRequest, ResourceTypeEnum resourceTypeEnum, Long courseId) {
        Integer total = courseResourceMapper.getAllCountByCourse(resourceTypeEnum, courseId);
        PageResponse pageResponse = getPageResponseByCourse(pageRequest, total, resourceTypeEnum, courseId);

        return RespBean.ok("查询成功", pageResponse);
    }

    private PageResponse getPageResponseForAll(PageRequest pageRequest, Integer total, ResourceTypeEnum resourceTypeEnum, Teacher teacher) {
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();

        List<CourseResource> courseResources = courseResourceMapper.getPage(pageUtil.getStart(), pageUtil.getPageSize(), resourceTypeEnum, teacher);
        List<CourseResourceDTO> courseResourceDTOS = getCourseResourceDTOsByCourseResources(courseResources);

        pageResponse.setList(courseResourceDTOS);
        pageResponse.setSize(courseResourceDTOS.size());

        return pageResponse;
    }

    private PageResponse getPageResponseByCourse(PageRequest pageRequest, Integer total, ResourceTypeEnum resourceTypeEnum, Long courseId) {
        PageUtil pageUtil = new PageUtil(pageRequest, total);
        PageResponse pageResponse = pageUtil.getPageResponse();

        List<CourseResource> courseResources = courseResourceMapper.getPageByCourse(pageUtil.getStart(), pageUtil.getPageSize(), resourceTypeEnum, courseId);
        List<CourseResourceDTO> courseResourceDTOS = getCourseResourceDTOsByCourseResources(courseResources);

        pageResponse.setList(courseResourceDTOS);
        pageResponse.setSize(courseResourceDTOS.size());

        return pageResponse;
    }

    private List<CourseResourceDTO> getCourseResourceDTOsByCourseResources(List<CourseResource> courseResources) {
        CourseResourceDTO dto = new CourseResourceDTO();
        return dto.convert(courseResources);
    }
}
