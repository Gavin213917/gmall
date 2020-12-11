package com.liskov.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liskov.gmall.bean.UserInfo;
import com.liskov.gmall.service.UserService;
import com.liskov.gmall.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> userInfoList() {
        return userInfoMapper.selectAll();
    }
}
