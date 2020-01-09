package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.FacultyDao;
import com.angiii.learnplatform.model.Faculty;
import com.angiii.learnplatform.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
