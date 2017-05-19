package com.example.service;

import com.example.db.entity.User;
import com.example.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2017/5/19.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserByUserName(String userName){
        return userMapper.selectUserByUserName(userName);
    }
}
