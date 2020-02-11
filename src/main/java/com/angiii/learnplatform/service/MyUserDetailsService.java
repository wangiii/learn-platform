package com.angiii.learnplatform.service;

import com.angiii.learnplatform.domain.entity.Student;
import com.angiii.learnplatform.mapper.AdminMapper;
import com.angiii.learnplatform.mapper.StudentMapper;
import com.angiii.learnplatform.mapper.TeacherMapper;
import com.angiii.learnplatform.domain.entity.Admin;
import com.angiii.learnplatform.domain.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Teacher teacher = teacherMapper.selectTeacherByPhoneForLoad(s);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if (teacher != null) {
            SimpleGrantedAuthority teacherAuthority = new SimpleGrantedAuthority("ROLE_TEACHER");
            authorities.add(teacherAuthority);
            return new User(teacher.getPhone(), teacher.getPassword(), authorities);
        }

        Admin admin = adminMapper.selectAdminByPhone(s);
        if (admin != null) {
            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(adminAuthority);
            return new User(admin.getPhone(), admin.getPassword(), authorities);
        }

        Student student = studentMapper.selectStudentByPhone(s);
        if (student != null) {
            SimpleGrantedAuthority studentAuthority = new SimpleGrantedAuthority("ROLE_STUDENT");
            authorities.add(studentAuthority);
            return new User(student.getPhone(), student.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }
}
