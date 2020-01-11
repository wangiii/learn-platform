package com.angiii.learnplatform.po;

import lombok.*;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Major extends BaseEntity implements Serializable {

    /**
     * 专业名称
     */
    private String name;
}
