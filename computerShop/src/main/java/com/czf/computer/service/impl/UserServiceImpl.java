package com.czf.computer.service.impl;

import com.czf.computer.domain.User;
import com.czf.computer.mapper.UserMapper;
import com.czf.computer.service.UserService;
import com.czf.computer.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    /*
    @Autowired 是按照类型注入
    @Resource 是按照变量名称注入
    * */
    @Resource
    private UserMapper userMapper;

    //定义一个md5算法的加密================================================================================================
    private String getMD5Password(String oldPassword,String salt){

        //md5加密算法的调用(进行3次)
        for(int i=0;i<3;i++){
            oldPassword = DigestUtils.md5DigestAsHex((salt+oldPassword+salt).getBytes()).toUpperCase();
        }
        String newPassword = oldPassword;
        return newPassword;
    }
    //=================================================================================================================
    @Override
    public void userInsert(User user) {
        String username = user.getUsername();
        User resultUser = userMapper.findByUsername(username);
        if (resultUser != null){
            throw new UsernameDuplicatedException("用户名已经被占用,请选择其他用户名");
        }

        //对密码进行加密: md5算法 --      (盐值+password+盐值) 连续加密3次
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(oldPassword,salt);
        //将加密之后的密码和盐值补全到user中
        user.setSalt(salt);
        user.setPassword(md5Password);
        //补全其他用户信息
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(new Date());
        user.setModifiedTime(new Date());

        //执行注册用户的功能
        int result = userMapper.insert(user);
        if(result != 1){
            throw new InsertException("用户注册失败");
        }


    }

    @Override
    public User userLogin(String username, String password) {
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            throw new UserNotFoundException("用户没找到，需要先注册");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户已被注销");
        }

        String salt = result.getSalt();
        String oldPassword = result.getPassword();
        if (!getMD5Password(password,salt).equals(oldPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }

        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 返回新的user对象
        return user;
    }

    @Override
    public void changePassword(int uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("该uid对应的用户不存在");
        }
        //输入的原密码与数据库的密码作比较
        String oldMd5Password = getMD5Password(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //设置新密码到数据库中
        String newMd5Password = getMD5Password(newPassword,result.getSalt());

        //mapper的方法 Integer updatePasswordByUid(int uid, String password, String modifiedUser, Date modifiedTime);
        int updateResult = userMapper.updatePasswordByUid(uid,newMd5Password,username, new Date() );
        if(updateResult != 1 ){
            throw new UpdateException("更新失败");
        }
    }

    @Override
    public User getMessageByUid(int uid) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("该uid对应的用户不存在");
        }
        return result;
    }

    //user中只有phone，email，gender。需要手动将uid，username封装进去
    @Override
    public void changeInfo(int uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("该uid对应的用户不存在");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        int updateResult = userMapper.updateInfoByUid(user);
        if(updateResult != 1 ){
            throw new UpdateException("更新失败");
        }
    }

    @Override
    public void updateAvatar(int uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("该uid对应的用户不存在");
        }
        int updateResult = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(updateResult != 1 ){
            throw new UpdateException("头像更新失败");
        }

    }


}
