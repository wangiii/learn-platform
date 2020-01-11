package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dao.AdminDao;
import com.angiii.learnplatform.dao.TeacherDao;
import com.angiii.learnplatform.po.Admin;
import com.angiii.learnplatform.po.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Teacher teacher = teacherDao.selectTeacherByPhone(s);
        if (teacher != null) {
            return new User(teacher.getPhone(), teacher.getPassword(),
                    new ArrayList<>());
        }

        Admin admin = adminDao.selectTeacherByPhone(s);
        if (admin != null) {
            return new User(admin.getPhone(), admin.getPassword(),
                    new ArrayList<>());
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }
}
