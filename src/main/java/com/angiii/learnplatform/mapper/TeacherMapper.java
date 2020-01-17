package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from tb_teacher")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Teacher> getAll();

    @Insert("insert into tb_teacher(name, phone, password, created_at, updated_at) " +
            "values(#{name}, #{phone}, #{password}, #{createTime}, #{updateTime})")
    int insert(Teacher teacher);

    @Select("select * from tb_teacher where phone = #{phone} limit 1")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Teacher selectTeacherByPhone(@Param("phone") String phone);
}
