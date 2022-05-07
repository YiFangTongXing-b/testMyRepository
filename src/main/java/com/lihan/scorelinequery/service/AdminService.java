package com.lihan.scorelinequery.service;
import com.lihan.scorelinequery.entity.Admin;


public interface AdminService {
    public Admin adminLogin(String admin_name,String admin_password);
}
