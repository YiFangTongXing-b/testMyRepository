package com.lihan.scorelinequery.controller;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.Util.UserExcelUtil;
import com.lihan.scorelinequery.entity.User;
import com.lihan.scorelinequery.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
@Controller
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/user_list")
    public String showUser(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           String user_name) {
        if (user_name == "null") {
            user_name = "";
        }
        log.info("user_name:{}", user_name);
        PageInfo<User> userList = userService.FindUser(pageNum, pageSize, user_name);
        model.addAttribute("userList", userList);
        log.info("isHas:{}", userList.isHasNextPage());
        model.addAttribute("user_name", user_name);
        return "user_list";
    }

    @RequestMapping(value = "/user_delete", method = RequestMethod.POST)
    public String deleteUser(Integer uid, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("uid:{}", uid);
        userService.DeleteUser(uid);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:user_list";
    }

    @RequestMapping(value = "/user_insert", method = RequestMethod.POST)
    public String insertUser(String user_name, String password, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("name:{}", user_name);
        log.info("password:{}", password);
        if (user_name == "" || password == "") {
            redirectAttributes.addFlashAttribute("msg", "添加失败，用户名和密码不能为空");
            return "redirect:user_list";
        }
        ;
        if (userService.SelectUser(null, user_name, null) != null) {
            redirectAttributes.addFlashAttribute("msg", "添加失败，用户名已存在");
            return "redirect:user_list";
        }
        userService.InsertUser(user_name, password);
        redirectAttributes.addFlashAttribute("msg", "添加成功");
        return "redirect:user_list";
    }

    @RequestMapping(value = "/user_update", method = RequestMethod.POST)
    public String updateUser(Integer uid, String user_name, String password, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("name:{}", user_name);
        log.info("password:{}", password);
        if (user_name == "" || password == "") {
            redirectAttributes.addFlashAttribute("msg", "修改失败，用户名和密码不能为空");
            return "redirect:user_list";
        }
        User user1 = userService.SelectUser(null, user_name, null);
        if (user1 != null && user1.getUid() != uid) {
            redirectAttributes.addFlashAttribute("msg", "修改失败，用户名已存在");
            return "redirect:user_list";
        }
        userService.UpdateUser(uid, user_name, password);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:user_list";
    }

    @RequestMapping("excel_user_insert")
    public String UserExcelInsert() {
        return "excel_user_insert";
    }

    @RequestMapping("upload_user")
    public String ExcelParseUser(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        String name = file.getOriginalFilename();
        System.out.println(name);
        if (!name.substring(name.length() - 4).equals(".xls")) {
            redirectAttributes.addFlashAttribute("msg", "文件解析错误");
            return "redirect:excel_user_insert";
        }
        Set<User> userSet = UserExcelUtil.excelToGetUser(file.getInputStream());
        for (User user : userSet) {
            User userTmp = userService.SelectUser(null, user.getUser_name(), null);
            if (userTmp == null) {
                userService.InsertUser(user.getUser_name(), user.getPassword());
            }

        }
        redirectAttributes.addFlashAttribute("msg","添加成功");
        return "redirect:excel_user_insert";
    }
}
