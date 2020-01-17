package com.angiii.learnplatform.domain.entity;

import lombok.*;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity implements Serializable {

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生手机号码
     */
    private String phone;

    /**
     * 学生密码
     */
    private String password;

    /**
     * 学生所属专业
     */
    private Major major;

    /**
     * 学生所属院系
     */
    private Faculty faculty;
}
