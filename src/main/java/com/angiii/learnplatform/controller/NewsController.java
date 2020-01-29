package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.News;
import com.angiii.learnplatform.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("news")
@CrossOrigin
public class NewsController {

    @Autowired
    NewsService newsService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean add(News news) {
        return newsService.save(news);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean getAll(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return newsService.all(pageRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean update(@PathVariable(name = "id") Long id, News news) {
        return newsService.update(id, news);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public RespBean delete(@PathVariable(name = "id") Long id) {
        return newsService.delete(id);
    }
}
