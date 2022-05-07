package com.lihan.scorelinequery.dao;

import com.lihan.scorelinequery.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminDao {
    @Select("Select * from  administrators WHERE admin_name = #{admin_name} AND admin_password = #{admin_password}")
    Admin SelectAdmin(@Param("admin_name") String admin_name, @Param("admin_password") String admin_password);
}
