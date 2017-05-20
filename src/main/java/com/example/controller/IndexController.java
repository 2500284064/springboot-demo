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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/thymeleaf", method = RequestMethod.GET)
    public String thymeleaf(){
        return "thymeleafIndex";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(String userName, String password){
        User user = userService.selectUserByUserName(userName);
        if(user != null && user.getPassword().equals(password)){
            return "redirect:/index";
        }
        return "error";
    }
}
