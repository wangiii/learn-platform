package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {

    @Select("select * from tb_admin where phone = #{phone} limit 1")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Admin selectAdminByPhone(@Param("phone") String phone);
}
