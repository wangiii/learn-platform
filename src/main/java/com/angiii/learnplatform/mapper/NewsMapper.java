package com.angiii.learnplatform.mapper;

import com.angiii.learnplatform.domain.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    @Insert("insert into tb_news(name, content, created_at, updated_at) " +
            "values(#{name}, #{content}, #{createTime}, #{updateTime})")
    int insert(News news);

    @Insert("insert into tb_news(name, content, teacher_id, created_at, updated_at) " +
            "values(#{name}, #{content}, #{teacher.id}, #{createTime}, #{updateTime})")
    int insertForTeacher(News news);

    @Select("select * from tb_news where ISNULL(teacher_id) ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<News> getPage(Integer start, Integer amount);

    @Select("select * from tb_news where teacher_id = #{id} ORDER BY id DESC limit #{start}, #{amount}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    List<News> getPageByTeacherId(Integer start, Integer amount, long id);

    @Select("select count(*) from tb_news where ISNULL(teacher_id)")
    Integer getAllCount();

    @Select("select count(*) from tb_news where teacher_id = #{id}")
    Integer getCountForTeacher(long id);

    @Delete("delete from tb_news where id = #{id}")
    int delete(Long id);

    @Update("update tb_news set " +
            "name = #{name}, content = #{content}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int update(News news);

    @Update("update tb_news set " +
            "name = #{name}, content = #{content}, teacher_id = #{teacher.id}, updated_at = #{updateTime} " +
            "where id = #{id}")
    int updateForTeacher(News news);

    @Select("select * from tb_news where id = #{id}")
    @Results({
            @Result(property = "createTime", column = "created_at"),
            @Result(property = "updateTime", column = "updated_at")
    })
    News selectNewsById(Long id);
}
