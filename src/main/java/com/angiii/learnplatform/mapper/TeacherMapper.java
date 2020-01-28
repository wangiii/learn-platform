package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from tb_teacher")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Teacher> getAll();

    @Insert("insert into tb_teacher(name, phone, password, faculty_id, created_at, updated_at) " +
            "values(#{name}, #{phone}, #{password}, #{faculty.id}, #{createTime}, #{updateTime})")
    int insert(Teacher teacher);

    @Select("select t.id as id, t.name as name, t.phone as phone, f.id as facultyId, f.name as facultyName, t.updated_at as updateTime, t.created_at as createTime " +
            "from tb_teacher t left join tb_faculty f on t.faculty_id = f.id where phone = #{phone} limit 1")
    @Results({
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName")
    })
    Teacher selectTeacherByPhone(@Param("phone") String phone);

    @Select("select * from tb_teacher where phone = #{phone} limit 1")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Teacher selectTeacherByPhoneForLoad(@Param("phone") String phone);

    @Select("select t.id as id, t.name as name, t.phone as phone, f.id as facultyId, f.name as facultyName, t.updated_at as updateTime, t.created_at as createTime " +
            "from tb_teacher t left join tb_faculty f on t.faculty_id = f.id ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName"),
            @Result(property = "majors", column = "id",
                    many = @Many(select = "com.angiii.learnplatform.mapper.MajorMapper.selectMajorsByTeacherId"))
    })
    List<Teacher> getPage(Integer start, Integer amount);

    @Select("select t.id as id, t.name as name, t.phone as phone, f.id as facultyId, f.name as facultyName, t.updated_at as updateTime, t.created_at as createTime " +
            "from tb_teacher t left join tb_faculty f on t.faculty_id = f.id " +
            "where t.phone like CONCAT(#{phone},'%') " +
            "ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName"),
            @Result(property = "majors", column = "id",
                    many = @Many(select = "com.angiii.learnplatform.mapper.MajorMapper.selectMajorsByTeacherId"))
    })
    List<Teacher> getSearch(Integer start, Integer amount, String phone);

    @Select("select count(*) from tb_teacher ")
    Integer getAllCount();

    @Select("select count(*) from tb_teacher where phone like CONCAT(#{phone},'%') ")
    Integer getSearchCount(String phone);

    @Update("update tb_teacher set " +
            "name = #{name}, faculty_id = #{faculty.id}, updated_at = #{updateTime} " +
            "where phone = #{phone}")
    int update(Teacher teacher);

    @Delete("delete from tb_teacher where phone = #{phone}")
    int delete(String phone);

    @Select("select id from tb_teacher where phone = #{phone}")
    long getIdByPhone(String phone);
}
