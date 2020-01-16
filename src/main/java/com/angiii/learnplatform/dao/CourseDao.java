package com.angiii.learnplatform.dao;

import com.angiii.learnplatform.po.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseDao {

    @Select("select * from tb_course")
    @Results({
            @Result(property = "classHour", column = "class_hour"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Course> getAll();

    @Select("select * from tb_course limit #{start}, #{amount}")
    @Results({
            @Result(property = "classHour", column = "class_hour"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Course> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_course ")
    Integer getAllCount();

    @Select("select * from tb_course where id = #{id}")
    @Results({
            @Result(property = "classHour", column = "class_hour"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Course selectCourseById(Long id);

    @Insert("insert into tb_course(name, cover, credit, semester, class_hour, created_at, updated_at) " +
            "values(#{name}, #{cover}, #{credit}, #{semester}, #{classHour}, #{createTime}, #{updateTime})")
    int insert(Course course);

    @Delete("delete from tb_course where id = #{id}")
    int delete(Long id);

    @Update("update tb_course set " +
            "name = #{name}, cover = #{cover}, credit = #{credit}, semester = #{semester}, class_hour = #{classHour}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(Course course);
}
