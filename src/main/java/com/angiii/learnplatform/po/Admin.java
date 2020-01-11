package com.angiii.learnplatform.po;

import lombok.*;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseEntity implements Serializable {

    /**
     * 管理员名称
     */
    private String name;

    /**
     * 管理员手机号码
     */
    private String phone;

    /**
     * 管理员密码
     */
    private String password;
}
