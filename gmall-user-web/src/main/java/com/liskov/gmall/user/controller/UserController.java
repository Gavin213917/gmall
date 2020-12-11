package com.liskov.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liskov.gmall.bean.UserInfo;
import com.liskov.gmall.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("userInfoList") //ResponseEntity 含有字符串转化的工具
    public ResponseEntity<List<UserInfo>> userInfoList() {
        //ctrl + alt + v 自动返回 对应的变量参数
        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);

    }
}
