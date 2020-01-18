package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.dto.MajorCheckBoxDTO;
import com.angiii.learnplatform.domain.dto.MajorDTO;
import com.angiii.learnplatform.domain.entity.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MajorMapper {

    @Insert("insert into tb_major(name, faculty_id, created_at, updated_at) " +
            "values(#{name}, #{faculty.id}, #{createTime}, #{updateTime})")
    int insert(Major major);

    @Select("select m.id as id, m.name as name, m.updated_at as updateTime, " +
            "m.created_at as createTime, f.id as facultyId, f.name as facultyName " +
            "from tb_major m left join tb_faculty f " +
            "on m.faculty_id = f.id ORDER BY m.id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName")
    })
    List<Major> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_major ")
    Integer getAllCount();

    @Delete("delete from tb_major where id = #{id}")
    int delete(Long id);

    @Update("update tb_major set " +
            "name = #{name}, faculty_id = #{faculty.id}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(Major major);

    @Select("select m.id as id, m.name as name, m.updated_at as updateTime, m.created_at as createTime, f.id as facultyId, f.name as facultyName from tb_major m left join tb_faculty f on m.faculty_id = f.id where m.id = #{id}")
    @Results({
            @Result(property = "faculty.id", column = "facultyId"),
            @Result(property = "faculty.name", column = "facultyName")
    })
    Major selectMajorById(Long id);

    @Select("SELECT * from tb_major where id IN (select major_id from tb_major_course where course_id = #{id})")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Major> selectMajorsByCourseId(Long id);

    @Select("select id, name from tb_major")
    @Results({
            @Result(property = "value", column = "id"),
            @Result(property = "label", column = "name")
    })
    List<MajorCheckBoxDTO> selectAllMajors();
}
