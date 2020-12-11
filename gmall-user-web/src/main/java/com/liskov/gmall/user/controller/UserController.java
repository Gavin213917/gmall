package com.liskov.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liskov.gmall.bean.UserInfo;
import com.liskov.gmall.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    //@Autowired //spring 自己的
    @Reference //dubbo 的注解
    UserService userService;

    @RequestMapping("userInfoList") //ResponseEntity 含有字符串转化的工具
    public ResponseEntity<List<UserInfo>> userInfoList() {
        //ctrl + alt + v 自动返回 对应的变量参数
        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);

    }
}
