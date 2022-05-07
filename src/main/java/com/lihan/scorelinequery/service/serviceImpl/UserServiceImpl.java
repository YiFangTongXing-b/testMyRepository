package com.lihan.scorelinequery.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.dao.UserDao;
import com.lihan.scorelinequery.entity.User;
import com.lihan.scorelinequery.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public PageInfo FindUser(Integer pageNum, Integer pageSize, String user_name) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> AllUser =userDao.SelectUsers(user_name);
        return new PageInfo<User>(AllUser);
    }

    @Override
    public void DeleteUser(Integer uid) {
        userDao.DeleteUser(uid);
    }

    @Override
    public void InsertUser(String user_name, String password) {
        userDao.InsertUser(user_name,password);
    }

    @Override
    public void UpdateUser(Integer uid,String user_name, String password) {
        userDao.UpdateUser(uid,user_name,password);
    }

    @Override
    public User SelectUser(Integer uid,String user_name,String password) {
        return userDao.SelectUser(uid,user_name,password);
    }
}
