package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lenovo on 2017/5/16.
 */
@Controller
public class IndexController {

    private static final String USER_NAME = "ftz";
    private static final String PASSWORD = "1234";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(String userName, String password){
        if(USER_NAME.equals(userName) && PASSWORD.equals(password))
            return "index";
        return "error";
    }
}
