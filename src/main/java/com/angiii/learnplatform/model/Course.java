package com.angiii.learnplatform.model;

import lombok.*;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity implements Serializable {

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程封面图 url
     */
    private String cover;

    /**
     * 所属学期
     */
    private String semester;

    /**
     * 课程学分
     */
    private Integer credit;

    /**
     * 课程学时
     */
    private Integer classHour;


}
