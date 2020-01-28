package com.angiii.learnplatform.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MajorCourseMapper {

    @Delete("delete from tb_major_course where course_id = #{courseId}")
    int delete(long courseId);

    @Insert("insert into tb_major_course(course_id, major_id) " +
            "values(#{courseId}, #{majorId})")
    int insert(long courseId, String majorId);
}
