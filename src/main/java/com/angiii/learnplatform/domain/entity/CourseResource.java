package com.angiii.learnplatform.domain.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseResource extends BaseEntity implements Serializable {

    /**
     * 资源标题
     */
    private String name;

    /**
     * 资源 url
     */
    private String url;

    /**
     * 所属院系
     */
    private Faculty faculty;

    /**
     * 所属课程
     */
    private Course course;

    /**
     * 上传教师
     */
    private Teacher teacher;

    /**
     * 资源类型
     */
    private ResourceTypeEnum type;
}
