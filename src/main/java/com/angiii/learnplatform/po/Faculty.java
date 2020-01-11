package com.angiii.learnplatform.po;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Faculty extends BaseEntity implements Serializable {

    /**
     * 院系名称
     */
    private String name;

    /**
     * 专业列表
     */
    private List<Major> majors;
}
