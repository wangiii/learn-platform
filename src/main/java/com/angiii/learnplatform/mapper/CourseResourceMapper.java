package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.CourseResource;
import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import com.angiii.learnplatform.domain.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseResourceMapper {

    @Insert("insert into tb_resource(name, url, faculty_id, course_id, teacher_id, type, created_at, updated_at) " +
            "values(#{name}, #{url}, #{faculty.id}, #{course.id}, #{teacher.id}, #{type}, #{createTime}, #{updateTime})")
    int insert(CourseResource courseResource);

    @Select("select r.id as id, r.name as name, r.url as url, r.type as type, " +
            "r.created_at as createTime, r.updated_at as updateTime, " +
            "f.id as facultyId, f.name as facultyName, " +
            "c.id as courseId, c.name as courseName, " +
            "t.id as teacherId, t.name as teacherName " +
            "from tb_resource r " +
            "left join tb_faculty f on r.faculty_id = f.id " +
            "left join tb_course c on r.course_id = c.id " +
            "left join tb_teacher t on r.teacher_id = t.id " +
            "where r.type = #{resourceTypeEnum} and r.teacher_id = #{teacher.id} ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName"),
            @Result(property = "course.id", column = "courseId"),
            @Result(property = "course.name", column = "courseName"),
            @Result(property = "teacher.id", column = "teacherId"),
            @Result(property = "teacher.name", column = "teacherName")
    })
    List<CourseResource> getPage(Integer start, Integer amount, ResourceTypeEnum resourceTypeEnum, Teacher teacher);

    @Select("select count(*) from tb_resource where type = #{resourceTypeEnum} and teacher_id = #{teacher.id}")
    Integer getAllCount(ResourceTypeEnum resourceTypeEnum, Teacher teacher);

    @Delete("delete from tb_resource where id = #{id}")
    int delete(Long id);

    @Update("update tb_resource set " +
            "name = #{name}, url = #{url}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(CourseResource courseResource);

    @Select("select r.id as id, r.name as name, r.url as url, r.type as type, " +
            "r.created_at as createTime, r.updated_at as updateTime, " +
            "f.id as facultyId, f.name as facultyName, " +
            "c.id as courseId, c.name as courseName, " +
            "t.id as teacherId, t.name as teacherName " +
            "from tb_resource r " +
            "left join tb_faculty f on r.faculty_id = f.id " +
            "left join tb_course c on r.course_id = c.id " +
            "left join tb_teacher t on r.teacher_id = t.id " +
            "where r.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName"),
            @Result(property = "course.id", column = "courseId"),
            @Result(property = "course.name", column = "courseName"),
            @Result(property = "teacher.id", column = "teacherId"),
            @Result(property = "teacher.name", column = "teacherName")
    })
    CourseResource selectCourseResourceById(Long id);
}
