package com.example.controller;

import com.example.db.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lenovo on 2017/5/16.
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    private static final String USER_NAME = "ftz";
    private static final String PASSWORD = "1234";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/thymeleaf", method = RequestMethod.GET)
    public String thymeleaf(){
        return "thymeleafIndex";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(String userName, String password){
        User user = userService.selectUserByUserName(userName);
        if(password.equals(user.getPassword()))
            return "index";
        return "error";
    }
}
