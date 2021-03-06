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

    @Select("select password from tb_student where phone = #{phone} limit 1")
    String selectStudentPasswordByPhone(@Param("phone") String phone);

    @Select("select s.id as id, s.name as name, s.phone as phone, " +
            "f.id as facultyId, f.name as facultyName, m.id as majorId, m.name as majorName, " +
            "s.updated_at as updateTime, s.created_at as createTime " +
            "from tb_student s left join tb_faculty f on s.faculty_id = f.id left join tb_major m on s.major_id = m.id ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "major.id", column = "majorId"),
            @Result(property = "major.name", column = "majorName"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName")
    })
    List<Student> getPage(Integer start, Integer amount);

    @Select("select s.id as id, s.name as name, s.phone as phone, " +
            "f.id as facultyId, f.name as facultyName, m.id as majorId, m.name as majorName, " +
            "s.updated_at as updateTime, s.created_at as createTime " +
            "from tb_student s left join tb_faculty f on s.faculty_id = f.id left join tb_major m on s.major_id = m.id " +
            "where s.phone like CONCAT(#{phone},'%') " +
            "ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "major.id", column = "majorId"),
            @Result(property = "major.name", column = "majorName"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName")
    })
    List<Student> getSearch(Integer start, Integer amount, String phone);

    @Select("select count(*) from tb_student ")
    Integer getAllCount();

    @Select("select count(*) from tb_student where phone like CONCAT(#{phone},'%') ")
    Integer getSearchCount(String phone);

    @Insert("insert into tb_student(name, phone, password, major_id, faculty_id, created_at, updated_at) " +
            "values(#{name}, #{phone}, #{password}, #{major.id}, #{faculty.id}, #{createTime}, #{updateTime})")
    int insert(Student student);

    @Update("update tb_student set " +
            "password = #{password}, updated_at = #{updateTime} " +
            "where phone = #{phone}")
    int update(Student student);

    @Delete("delete from tb_student where phone = #{phone}")
    int delete(String phone);
}
