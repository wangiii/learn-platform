package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.dto.FacultyDTO;
import com.angiii.learnplatform.domain.dto.FacultyResponse;
import com.angiii.learnplatform.domain.entity.Faculty;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FacultyMapper {

    @Insert("insert into tb_faculty(name, created_at, updated_at) " +
            "values(#{name}, #{createTime}, #{updateTime})")
    int insert(Faculty faculty);

    @Select("select * from tb_faculty ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Faculty> getPage(Integer start, Integer amount);

    @Select("select id as value, name as label from tb_faculty")
    List<FacultyDTO> getFacultyDTO();

    @Select("select id, name from tb_faculty")
    List<FacultyResponse> getFacultyResponse();

    @Select("select count(*) from tb_faculty ")
    Integer getAllCount();

    @Delete("delete from tb_faculty where id = #{id}")
    int delete(Long id);

    @Update("update tb_faculty set " +
            "name = #{name}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(Faculty faculty);

    @Select("select * from tb_faculty where id = #{id}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Faculty selectFacultyById(Long id);

    @Select("select * from tb_faculty ORDER BY id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "majors", column = "id",
                    many = @Many(select = "com.angiii.learnplatform.mapper.MajorMapper.selectMajorsByFaculty")),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Faculty> getAllWithMajor();

    @Select("select * from tb_faculty")
    List<Faculty> selectAllFaculty();

    @Select("select COUNT(DISTINCT `course_id`) from `tb_major_course` where `major_id` in (select id from tb_major where `faculty_id` = #{id})")
    int selectCourseCountByFaculty(Faculty faculty);
}
