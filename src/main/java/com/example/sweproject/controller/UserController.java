package com.example.sweproject.controller;

import com.example.sweproject.bean.*;
import com.example.sweproject.service.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    @Autowired
    private UserServiceImp userServiceImp;
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonMessage addUser(User user)
    {
        CommonMessage message=new CommonMessage();
        if(userServiceImp.getUserByUserName(user.getUserName())==null)
        {
            message.setState(userServiceImp.addUser(user));
            message.setMessage("注册成功！");
            return message;
        }
        else
        {
            message.setState(0);
            message.setMessage("已存在该用户名，注册失败！");
            return message;
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginMessage login(User user)
    {
        LoginMessage loginMessage=new LoginMessage();
        User temp=userServiceImp.getUserByUserName(user.getUserName());
        if(temp==null)
        {
            loginMessage.setState(0);
            loginMessage.setMessage("用户名不存在！");
            return loginMessage;
        }
        else if(!temp.getPassword().equals(user.getPassword()))
        {
            loginMessage.setState(0);
            loginMessage.setMessage("密码错误！");
            return loginMessage;
        }
        else
        {
            loginMessage.setState(1);
            loginMessage.setMessage("登陆成功！");
            loginMessage.setUserID(temp.getUserID());
            loginMessage.setAuthority(temp.getAuthority());
            return loginMessage;
        }
    }

    @RequestMapping(value="/saveUserInfo",method = RequestMethod.POST)
    public CommonMessage saveUserInfo(int userID, UserInfo userInfo, Address address)
    {
        CommonMessage commonMessage=new CommonMessage();
        if(userServiceImp.saveUserInfo(userID,userInfo)==1&&userServiceImp.savaUserDormitory(userID,address)==1)
        {
            commonMessage.setState(1);
            commonMessage.setMessage("保存成功！");
            return commonMessage;
        }
        else
        {
            commonMessage.setState(0);
            commonMessage.setMessage("保存失败！");
            return commonMessage;
        }
    }
}
