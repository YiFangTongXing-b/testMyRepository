package com.czf.computer.controller;

import com.czf.computer.controller.ex.*;
import com.czf.computer.service.ex.*;
import com.czf.computer.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;


//控制层的基类，主要做异常的捕获处理,以及获取登陆用户的uid和username
public class BaseController {

    public static final  int OK = 200;    //操作成功的状态码
    public BaseController() {
    }

    //当项目产生异常，会被拦截到此方法中
    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>();
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用，请重新输入用户名");
        }else if(e instanceof InsertException){
            result.setState(4001);
            result.setMessage("未知错误，注册失败");
        }else if(e instanceof UserNotFoundException){
            result.setState(4002);
            result.setMessage("用户不存在，请先注册");
        }else if(e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("地址数量超出上限");
        }else if(e instanceof CartNotFoundException) {
            result.setState(4004);
            result.setMessage("购物车数据不存在");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(5000);
            result.setMessage("密码错误，请重新输入密码");
        }else if(e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("更新失败");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("更新失败");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("图片大小超出限制");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("图片类型超出限制，请选择别的图片格式");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("图片状态异常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("图片传输异常");
        }
        return result;
    }

    //获取登陆用户的uid和username
    public int getUidFromSession(HttpSession session){
        //或者这样写    Integer.parseInt((String) session.getAttribute("uid"));
        return Integer.parseInt(session.getAttribute("uid").toString());
    }
    public String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
