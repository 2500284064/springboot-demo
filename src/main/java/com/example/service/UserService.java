package com.example.service;

import com.example.db.entity.Right;
import com.example.db.entity.Role;
import com.example.db.entity.User;
import com.example.db.mapper.RightMapper;
import com.example.db.mapper.RoleMapper;
import com.example.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2017/5/19.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RightMapper rightMapper;
    @Autowired
    private RoleMapper roleMapper;

    public User selectUserByUserName(String userName){
        return userMapper.selectUserByUserName(userName);
    }

    public List<Role> selectRolesByUserId(String userId) {
        return roleMapper.selectRoleByUserId(userId);
    }

    public List<Right> selectRightsByUserId(String userId) {
        return rightMapper.selectRightByUserId(userId);
    }
}
