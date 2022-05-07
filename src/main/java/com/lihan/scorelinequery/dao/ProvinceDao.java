package com.lihan.scorelinequery.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProvinceDao {
    @Select("SELECT name FROM provinces WHERE pid=#{pid}")
    String getProvinceName(@Param("pid") Integer pid);

    @Select("SELECT pid FROM provinces WHERE name = #{province_name}")
    Integer getProvincePid(@Param("province_name") String province_name);
}
