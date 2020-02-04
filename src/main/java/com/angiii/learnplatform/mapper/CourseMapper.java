package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.dto.CourseDTO;
import com.angiii.learnplatform.domain.entity.Course;
import com.angiii.learnplatform.domain.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("select id as value, name as label from tb_course " +
            "where id in (select course_id from tb_teacher_course where teacher_id = #{id})")
    List<CourseDTO> getAllDto(Teacher teacher);

    @Select("select id as value, name as label from tb_course ")
    List<CourseDTO> getAllDtoForAdmin();

    @Select("select * from tb_course ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "classHour", column = "class_hour"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at"),
            @Result(property = "majors", column = "id",
            many = @Many(select = "com.angiii.learnplatform.mapper.MajorMapper.selectMajorsByCourseId"))
    })
    List<Course> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_course ")
    Integer getAllCount();

    @Select("select count(*) from tb_course where id IN (select course_id from tb_major_course where major_id = #{majorId})")
    Integer getCountByMajor(Long majorId);

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

    @Select("SELECT * from tb_course where id IN (select course_id from tb_teacher_course where teacher_id = #{id})")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Course> selectCoursesByTeacherId(Long id);

    @Select("SELECT * from tb_course where id IN (select course_id from tb_major_course where major_id = #{majorId}) ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Course> selectCoursesByMajorId(Long majorId, Integer start, Integer amount);
}
