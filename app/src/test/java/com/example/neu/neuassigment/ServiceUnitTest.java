package com.example.neu.neuassigment;

import com.example.neu.neuassigment.gson.ResultDTO;
import com.example.neu.neuassigment.service.UserService;

import junit.framework.Assert;

import org.junit.Test;

public class ServiceUnitTest {
    @Test
    public void testLogin(){
        UserService userService=new UserService();
        ResultDTO result_success = userService.login("root", "asdasdasd");
        System.out.println(result_success);
        Assert.assertEquals(1,result_success.getState());
        Assert.assertEquals("登录成功！",result_success.getMsg());

        ResultDTO result_fail = userService.login("root", "asdasdasdaaa");
        System.out.println(result_fail);
        Assert.assertEquals(0,result_fail.getState());
        Assert.assertEquals("登陆失败，用户名或密码错误",result_fail.getMsg());

        ResultDTO result_no_register = userService.login("retgqegqawe", "asdasdasdaaa");
        System.out.println(result_no_register);
        Assert.assertEquals(2,result_no_register.getState());
        Assert.assertEquals("登陆失败，用户尚未注册",result_no_register.getMsg());
    }
    @Test
    public void testRegister(){
        UserService userService=new UserService();
        ResultDTO resultDTO = userService.login("root", "asdasdasd");
        System.out.println(resultDTO);

    }
    @Test
    public void testChangeProfile(){
        UserService userService=new UserService();
        ResultDTO resultDTO=userService.changeProfile("root","哈哈",
                "成人","身份证","44444","55555");
        System.out.println(resultDTO);
    }
}
