package com.angiii.learnplatform.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends BaseEntity implements Serializable {

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 教师手机号码
     */
    private String phone;

    /**
     * 教师密码
     */
    private String password;

    /**
     * 教师所属院系
     */
    private Faculty faculty;

    /**
     * 教师所属专业列表
     */
    private List<Major> majors;

    /**
     * 教师的课程列表
     */
    private List<Course> courses;
}
