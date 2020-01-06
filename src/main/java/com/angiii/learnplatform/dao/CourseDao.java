package com.angiii.learnplatform.dao;

import com.angiii.learnplatform.model.Course;
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

    @Insert("insert into tb_course(name, cover, credit, semester, class_hour, created_at, updated_at) " +
            "values(#{name}, #{cover}, #{credit}, #{semester}, #{classHour}, #{createTime}, #{updateTime})")
    void insert(Course course);
}
