package com.example.neu.neuassigment.gson;

import com.example.neu.neuassigment.bean.User;

import lombok.Data;

/**
 * 登录、注册、更改个人信息
 *请求服务端后返回的结果
 */
@Data
public class ResultDTO {
    int state;
    String msg;
    User user;
}
