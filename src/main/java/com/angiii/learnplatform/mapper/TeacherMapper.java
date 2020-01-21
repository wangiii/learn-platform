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

    @Select("select * from tb_teacher ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at"),
    })
    List<Teacher> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_teacher ")
    Integer getAllCount();

    @Update("update tb_teacher set " +
            "name = #{name}, password = #{password}, updated_at = #{updateTime} " +
            "where phone = #{phone}")
    int update(Teacher teacher);

    @Delete("delete from tb_teacher where phone = #{phone}")
    int delete(String phone);
}
