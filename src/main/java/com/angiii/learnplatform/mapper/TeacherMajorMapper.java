package com.angiii.learnplatform.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMajorMapper {

    @Delete("delete from tb_teacher_major where teacher_id = #{teacherId}")
    int delete(long teacherId);

    @Insert("insert into tb_teacher_major(teacher_id, major_id) " +
            "values(#{teacherId}, #{majorId})")
    int insert(long teacherId, String majorId);
}
