package com.lihan.scorelinequery.service;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.entity.User;

public interface UserService {
    public User SelectUser(Integer uid,String user_name,String password);
    public PageInfo FindUser(Integer pageNum, Integer pageSize, String user_name);
    public void DeleteUser(Integer uid);
    public void InsertUser(String user_name,String password);
    public void UpdateUser(Integer uid,String user_name,String password);
}
