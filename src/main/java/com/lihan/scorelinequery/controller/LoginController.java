package com.lihan.scorelinequery.controller;

import com.lihan.scorelinequery.entity.Admin;
import com.lihan.scorelinequery.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
public class LoginController {

    @Resource
    AdminService adminService;

    @Autowired
    private HttpSession session;


    @RequestMapping(value = "/login")
    public String adminLogin(Model model,String admin_name, String admin_password){
        if(admin_name==null&&admin_password==null){
            return "login";
        }
        log.info("name:{}",admin_name);
        log.info("password:{}",admin_password);
        Admin admin=adminService.adminLogin(admin_name,admin_password);
        if(admin!=null){
            session.setAttribute("admin_name",admin.getAdmin_name());
            return "redirect:user_list";
        }
        else {
            String msg="用户名或密码错误";
            model.addAttribute("msg", msg);
            return "login";
        }
    }

    @RequestMapping("exit")
    public String Exit(){
        session.removeAttribute("admin_name");
        return "redirect:login";
    }

}
