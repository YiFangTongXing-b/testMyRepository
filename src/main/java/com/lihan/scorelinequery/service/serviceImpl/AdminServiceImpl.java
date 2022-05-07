package com.lihan.scorelinequery.service.serviceImpl;

import com.lihan.scorelinequery.dao.AdminDao;
import com.lihan.scorelinequery.entity.Admin;
import com.lihan.scorelinequery.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Override
    public Admin adminLogin(String admin_name, String admin_password) {
        return adminDao.SelectAdmin(admin_name,admin_password);
    }
}
