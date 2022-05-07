package com.lihan.scorelinequery.dao;
import com.lihan.scorelinequery.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    //查询
    @Select({"<script>",
            "SELECT * FROM users WHERE 1=1",
            "<when test='uid!=null'>",
                "AND uid = '${uid}'",
            "</when>",
            "<when test='user_name!=null'>",
                "AND user_name = '${user_name}'",
            "</when>",
            "<when test='password!=null'>",
                "AND password = '${password}'",
            "</when>",
            "</script>"})
    User SelectUser(@Param("uid")Integer uid,@Param("user_name") String user_name,@Param("password") String password);


    @Select({"<script>",
            "SELECT * FROM users",
            "<when test=\"user_name != 'null'\">",
                "where user_name LIKE '%${user_name}%'",
            "</when>",
            "</script>"})
    List<User> SelectUsers(@Param("user_name") String user_name);

    //增加
    @Insert("insert into users(user_name,password)values(#{user_name},#{password})")
    void InsertUser(@Param("user_name") String user_name, @Param("password") String password);

    //删除
    @Delete("DELETE FROM users WHERE uid = #{uid}")
    void DeleteUser(@Param("uid")Integer uid);

    //更新
    @Update("UPDATE users SET user_name=#{user_name}, password=#{password} WHERE uid=#{uid}")
    void UpdateUser(@Param("uid") Integer uid ,@Param("user_name") String user_name,@Param("password") String password);
}
