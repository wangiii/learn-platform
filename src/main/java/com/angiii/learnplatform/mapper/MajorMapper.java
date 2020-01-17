package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MajorMapper {

    @Insert("insert into tb_major(name, faculty_id, created_at, updated_at) " +
            "values(#{name}, #{facultyId}, #{createTime}, #{updateTime})")
    int insert(Major major);

    @Select("select * from tb_major limit #{start}, #{amount}")
    @Results({
            @Result(property = "facultyId", column = "faculty_id"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Major> getPage(Integer start, Integer amount);

    @Select("select count(*) from tb_major ")
    Integer getAllCount();

    @Delete("delete from tb_major where id = #{id}")
    int delete(Long id);

    @Update("update tb_major set " +
            "name = #{name}, faculty_id = #{facultyId}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(Major major);

    @Select("select * from tb_major where id = #{id}")
    @Results({
            @Result(property = "facultyId", column = "faculty_id"),
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    Major selectMajorById(Long id);
}
