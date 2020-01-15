package com.angiii.learnplatform.dao;

import com.angiii.learnplatform.po.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminDao {

    @Select("select * from tb_admin where phone = #{phone} limit 1")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Admin selectAdminByPhone(@Param("phone") String phone);
}
