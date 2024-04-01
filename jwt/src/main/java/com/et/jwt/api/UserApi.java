package com.et.jwt.api;


import com.alibaba.fastjson.JSONObject;
import com.et.jwt.annotation.UserLoginToken;
import com.et.jwt.entity.User;
import com.et.jwt.service.TokenService;
import com.et.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date 2020-05-26
 */

@RestController
@RequestMapping("api")
//在数据访问接口中加入登录操作注解
//不加注解的话默认不验证，登录接口一般是不验证的
public class UserApi {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    //登录
    @PostMapping("/login")
    public Object login( User user){
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findByUsername(user);
        if(userForBase==null){
            jsonObject.put("message","login fail,user is not exist");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","login fail,password wrong");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    
    @UserLoginToken
    @GetMapping("/getMessage")
    //登录注解，说明该接口必须登录获取token后，在请求头中加上token并通过验证才可以访问
    public String getMessage(){
        return "verify pass";
    }
}
