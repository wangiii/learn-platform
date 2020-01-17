package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.RespBean;
import com.angiii.learnplatform.domain.entity.Major;
import com.angiii.learnplatform.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("major")
@CrossOrigin
public class MajorController {

    @Autowired
    MajorService majorService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean addMajor(Major major) {
        return majorService.save(major);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean getAllMajor(PageRequest pageRequest) {
        log.info("pageRequest{}", pageRequest);
        return majorService.all(pageRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean deleteMajor(@PathVariable(name = "id") Long id) {
        return majorService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RespBean updateMajor(@PathVariable(name = "id") Long id, Major major) {
        return majorService.update(id, major);
    }
}
