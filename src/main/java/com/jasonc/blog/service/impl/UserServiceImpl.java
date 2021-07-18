package com.jasonc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jasonc.blog.entity.User;
import com.jasonc.blog.mapper.UserMapper;
import com.jasonc.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String username, String password) {
        // QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // Map<String , Object> map = new HashMap<>();
        // map.put("username", username);
        // map.put("password", password);
        // queryWrapper.allEq(map);
        // User user = userMapper.selectOne(queryWrapper);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password));
        return user;
    }

    @Override
    public User getUser(String username) {
        // QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("username", username);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        return user;
    }
}
