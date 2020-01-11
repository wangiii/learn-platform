package com.angiii.learnplatform.dao;

import com.angiii.learnplatform.po.Faculty;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacultyDao {

    @Insert("insert into tb_faculty(name, created_at, updated_at) " +
            "values(#{name}, #{createTime}, #{updateTime})")
    int insert(Faculty faculty);
}
