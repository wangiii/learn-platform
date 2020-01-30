package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.News;
import com.angiii.learnplatform.domain.entity.Teacher;
import com.angiii.learnplatform.mapper.NewsMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsService {

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public RespBean save(News news) {
        if (news != null && news.getName() != null) {
            news.setUpdateTime(new Date());
            news.setCreateTime(new Date());
            if (AuthUtil.getRoles().contains("ROLE_ADMIN")) {
                if (newsMapper.insert(news) == 1) {
                    return RespBean.ok("添加成功", news);
                }
            }
            if (AuthUtil.getRoles().contains("ROLE_TEACHER")) {
                Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
                if (teacher == null) {
                    throw new IllegalArgumentException("教师手机有误");
                }
                news.setTeacher(teacher);
                if (newsMapper.insertForTeacher(news) == 1) {
                    return RespBean.ok("添加成功", news);
                }
            }
        }
        throw new IllegalArgumentException("添加失败");
    }

    public RespBean delete(Long id) {
        if (newsMapper.delete(id) == 1) {
            return RespBean.ok("删除成功");
        }
        throw new IllegalArgumentException("删除失败");
    }

    public RespBean update(Long id, News news) {
        if (news != null
                && news.getName() != null) {
            news.setId(id);
            news.setUpdateTime(new Date());
            if (AuthUtil.getRoles().contains("ROLE_ADMIN")) {
                if (newsMapper.update(news) == 1) {
                    News realNews = newsMapper.selectNewsById(news.getId());
                    return RespBean.ok("更新成功", realNews);
                }
            }
            if (AuthUtil.getRoles().contains("ROLE_TEACHER")) {
                Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
                if (teacher == null) {
                    throw new IllegalArgumentException("教师手机有误");
                }
                news.setTeacher(teacher);
                if (newsMapper.updateForTeacher(news) == 1) {
                    News realNews = newsMapper.selectNewsById(news.getId());
                    return RespBean.ok("更新成功", realNews);
                }
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
        if (AuthUtil.getRoles().contains("ROLE_ADMIN")) {
            Integer total = newsMapper.getAllCount();
            Integer pages = total / pageSize + 1;
            PageResponse pageResponse = PageResponse.builder().
                    pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
            List<News> news = newsMapper.getPage(start, amount);
            pageResponse.setList(news);
            pageResponse.setSize(news.size());

            return RespBean.ok("查询成功", pageResponse);
        }
        if (AuthUtil.getRoles().contains("ROLE_TEACHER")) {
            Teacher teacher = teacherMapper.selectTeacherByPhone(AuthUtil.getAuthPhone());
            if (teacher == null) {
                throw new IllegalArgumentException("教师手机有误");
            }
            Integer total = newsMapper.getCountForTeacher(teacher.getId());
            Integer pages = total / pageSize + 1;
            PageResponse pageResponse = PageResponse.builder().
                    pageNum(pageNum).pageSize(pageSize).total(total).pages(pages).build();
            List<News> news = newsMapper.getPageByTeacherId(start, amount, teacher.getId());
            pageResponse.setList(news);
            pageResponse.setSize(news.size());

            return RespBean.ok("查询成功", pageResponse);
        }
        throw new IllegalArgumentException("查询失败");
    }
}
