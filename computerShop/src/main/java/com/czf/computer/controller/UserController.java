package com.czf.computer.controller;

import com.czf.computer.controller.ex.*;
import com.czf.computer.domain.User;
import com.czf.computer.service.UserService;
import com.czf.computer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    //注册
    @RequestMapping("/register")
    public JsonResult<Void> register(User user) {
        userService.userInsert(user);
        return new JsonResult<>(OK);
    }

    //登陆
    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = userService.userLogin(username,password);
        //springboot的session是全局的。这两行完成session的赋值
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        return new JsonResult<User>(OK,user);
    }

    //修改密码
    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    //查询用户信息
    @RequestMapping("/getMessage_ByUid")
    public JsonResult<User> getMessageByUid(HttpSession session){
        User result = userService.getMessageByUid(getUidFromSession(session));
        return new JsonResult<>(OK,result);
    }

    //更新用户信息
    @RequestMapping("/change_Info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user中有 username,phone,email,gender 这4个属性值
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    //更新头像
    public static final int AVATAR_MAX_SIZE = 10*1021*1024;       //头像最大值10M
    public static final List<String> AVATAR_TYPE = new ArrayList<>();    //头像类型限定4种
    static {
            AVATAR_TYPE.add("image/jpeg");
            AVATAR_TYPE.add("image/png");
            AVATAR_TYPE.add("image/bmp");
            AVATAR_TYPE.add("image/gif");
    }

    //MultipartFile是springmvc提供的接口，可以获取文件类型的数据
    @RequestMapping("/change_Avatar")                           //这个file是前端页面的属性名
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file){
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("上传的头像文件不允许为空");
        }
        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }
        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持，允许的文件类型：" + AVATAR_TYPE);
        }
        // 获取当前项目的绝对磁盘路径
        String parent = session.getServletContext().getRealPath("upload");
        System.out.println("头像保存的位置为"+parent);
        // 保存头像文件的文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();     //如果目录不存在，则新建当前目录
        }

        // 保存的头像文件的文件名
        String suffix = "";      //后缀
        String originalFilename = file.getOriginalFilename();
        int pointIndex = originalFilename.lastIndexOf(".");    //点.的下表位置
        if (pointIndex > 0) {
            suffix = originalFilename.substring(pointIndex);     //例如 .jpg
        }
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        // 创建文件对象，表示保存的头像文件，dir是目录结构
        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);          //将file文件的数据写入到dest文件中，前提是文件后缀名相同
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，可能被删除/移动/修改");
        } catch (IOException e) {
            throw new FileUploadIOException("上传文件时读写异常");
        }

        // 头像最后保存的名称
        String avatar = "/upload/" + filename;
        System.out.println("头像全称"+avatar);
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 将头像写入到数据库中
        userService.updateAvatar(uid, avatar,username);
        // 返回成功头像路径
        return new JsonResult<>(OK, avatar);
    }










}
