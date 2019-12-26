package com.example.neu.neuassigment.service;

import com.example.neu.neuassigment.bean.User;
import com.example.neu.neuassigment.gson.LoginDTO;
import com.example.neu.neuassigment.gson.ResultDTO;
import com.example.neu.neuassigment.util.HandleRequestUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserService {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOGIN_URL="http://192.168.226.130:8080/login";
    private static final String REGISTER_URL="http://192.168.226.130:8080/register";
    private static final String CHANGE_PROFILE_URL="http://192.168.226.130:8080/changeProfile";
    private Gson gson=new Gson();

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ResultDTO login(String username,String password){
        return loginOrRegister(username,password,LOGIN_URL);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public ResultDTO register(String username,String password){
        return loginOrRegister(username,password,REGISTER_URL);
    }

    /**
     * 修改个人信息
     * @param username
     * @param name
     * @param userType
     * @param idCardType
     * @param idCardNumber
     * @param phoneNumber
     * @return
     */
    public ResultDTO changeProfile(String username,String name,String userType,
                                   String idCardType,String idCardNumber,String phoneNumber){
        User user=new User();
        user.setUsername(username);
        user.setName(name);
        user.setUserType(userType);
        user.setIdCardType(idCardType);
        user.setIdCardNumber(idCardNumber);
        user.setPhoneNumber(phoneNumber);
        String json=gson.toJson(user);
        //构建Request
        RequestBody requestBody=RequestBody.create(JSON,json);
        Request request=new Request.Builder().post(requestBody).url(CHANGE_PROFILE_URL).build();
        //发送Request得到响应
        HandleRequestUtil handleRequestUtil=new HandleRequestUtil();
        String response = handleRequestUtil.sendRequestWithOkHttp(request);
        //返回请求结果
        return parseResponse(response);
    }

    //登录注册共有逻辑
    private ResultDTO loginOrRegister(String username,String password,String URL){
        //将登录信息转Json字符串
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
        String loginJson=gson.toJson(loginDTO);
        //构建Request
        RequestBody requestBody=RequestBody.create(JSON,loginJson);
        Request request=new Request.Builder().post(requestBody).url(URL).build();
        //发送Request得到响应
        HandleRequestUtil handleRequestUtil=new HandleRequestUtil();
        String response = handleRequestUtil.sendRequestWithOkHttp(request);
        //返回请求结果
        return parseResponse(response);
    }

    private ResultDTO parseResponse(String response){
        ResultDTO resultDTO=null;
        JsonParser parser=new JsonParser();
        try{
            JsonElement element=parser.parse(response);
            JsonObject jsonObject=element.getAsJsonObject();
            resultDTO=gson.fromJson(jsonObject,ResultDTO.class);
        }catch(Exception e){
            resultDTO=new ResultDTO();
            resultDTO.setState(-1);
            resultDTO.setMsg("无法连接到远程服务器！");
        }
        return resultDTO;
    }

}
