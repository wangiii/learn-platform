package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from tb_student where phone = #{phone} limit 1")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Student selectStudentByPhone(@Param("phone") String phone);

    @Select("select * from tb_student ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at"),
    })
    List<Student> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_student ")
    Integer getAllCount();

    @Insert("insert into tb_student(name, phone, password, created_at, updated_at) " +
            "values(#{name}, #{phone}, #{password}, #{createTime}, #{updateTime})")
    int insert(Student student);

    @Update("update tb_student set " +
            "name = #{name}, password = #{password}, updated_at = #{updateTime} " +
            "where phone = #{phone}")
    int update(Student student);

    @Delete("delete from tb_student where phone = #{phone}")
    int delete(String phone);
}
