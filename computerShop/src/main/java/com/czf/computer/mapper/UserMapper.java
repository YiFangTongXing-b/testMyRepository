package com.czf.computer.mapper;

import com.czf.computer.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块的持久层接口
@Mapper
public interface UserMapper {

    Integer insert(User user);

    User findByUsername(String username);

    User findByUid(int uid);
    Integer updatePasswordByUid(@Param("uid") int uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);

    int updateInfoByUid(User user);      //修改用户资料

    int updateAvatarByUid(@Param("uid") int uid,
                          @Param("avatar") String avatar,
                          @Param("modifiedUser") String modifiedUser,
                          @Param("modifiedTime") Date modifiedTime);       //更根据uid修改头像


}
