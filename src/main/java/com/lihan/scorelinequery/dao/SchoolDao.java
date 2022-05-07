package com.lihan.scorelinequery.dao;

import com.lihan.scorelinequery.entity.School;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolDao {
    //查询
    @Select({"<script>",
            "SELECT schools.school_id, schools.name as school_name, schools.site, provinces.name as province_name FROM schools LEFT JOIN provinces ON schools.pid=provinces.pid WHERE 1=1",
            "<when test=\"school_name!='null'\">",
            "AND schools.name LIKE '%${school_name}%'",
            "</when>",
            "<when test=\"province_name!='null'\">",
            "AND provinces.name LIKE '%${province_name}%'",
            "</when>",
            "</script>"})
    List<School> SelectSchools(@Param("school_name") String school_name, @Param("province_name") String province_name);


    @Select("SELECT * from schools where name=#{school_name}")
    School SelectSchool(@Param("school_name")String school_name);


    //增加
    @Insert("INSERT INTO schools(name,pid,site) values(#{school_name},#{pid},#{site})")
    void InsertSchool(@Param("school_name") String school_name, @Param("pid") Integer pid, @Param("site") String site);

    //删除
    @Delete("DELETE FROM schools WHERE school_id = #{school_id}")
    void DeleteSchool(@Param("school_id") Integer school_id);

    //更新
    @Update("Update schools SET name=#{school_name},pid=#{pid},site=#{site} WHERE school_id=#{school_id}")
    void UpdateSchool(@Param("school_id") Integer school_id, @Param("school_name") String school_name, @Param("pid") Integer pid, @Param("site") String site);
}
