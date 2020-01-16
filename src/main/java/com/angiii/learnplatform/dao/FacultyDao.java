package com.angiii.learnplatform.dao;

import com.angiii.learnplatform.po.Faculty;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FacultyDao {

    @Insert("insert into tb_faculty(name, created_at, updated_at) " +
            "values(#{name}, #{createTime}, #{updateTime})")
    int insert(Faculty faculty);

    @Select("select * from tb_faculty limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<Faculty> getPage(Integer start, Integer amount);

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
}
