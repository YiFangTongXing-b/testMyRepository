package com.czf.computer.service;

import com.czf.computer.domain.User;

import java.util.Date;


//用户模块业务层接口
public interface UserService {
    void userInsert(User user);   //用户注册
    User userLogin(String username,String password);   //用户登陆
    void changePassword(int uid,String username,String oldPassword,String newPassword);    //修改密码

    User getMessageByUid(int uid);            //根据uid获取用户信息
    void changeInfo(int uid,String username,User user);   //更新用户资料。uid和username将在controller层，从session获取，封装进user中

    void updateAvatar(int uid, String avatar, String username);   //更新头像
}
