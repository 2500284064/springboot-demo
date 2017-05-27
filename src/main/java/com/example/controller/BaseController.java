package com.example.controller;

import com.example.db.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by lenovo on 2017/5/26.
 */
public class BaseController {

    protected User currentUser(){
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getPrincipal();
    }
}
