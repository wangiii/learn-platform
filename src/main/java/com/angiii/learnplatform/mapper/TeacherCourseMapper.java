package com.angiii.learnplatform.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherCourseMapper {

    @Delete("delete from tb_teacher_course where teacher_id = #{teacherId}")
    int delete(long teacherId);

    @Insert("insert into tb_teacher_course(course_id, teacher_id) " +
            "values(#{courseId}, #{teacherId})")
    int insert(long teacherId, String courseId);
}
