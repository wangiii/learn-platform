package com.angiii.learnplatform.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentCourseMapper {

    @Delete("delete from tb_student_course where student_id = #{studentId}")
    int delete(long studentId);

    @Insert("insert into tb_student_course(course_id, student_id) " +
            "values(#{courseId}, #{studentId})")
    int insert(long studentId, String courseId);
}
