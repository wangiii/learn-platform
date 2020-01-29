package com.angiii.learnplatform.domain.entity;

import lombok.*;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseEntity implements Serializable {

    /**
     * 新闻通知标题
     */
    private String name;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 所属教师
     */
    private Teacher teacher;
}
