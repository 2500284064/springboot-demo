package com.example.controller.aboutUs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 2017/10/27.
 */
@Controller
@RequestMapping("/aboutUs")
public class AboutUsController {

    @RequestMapping("/home")
    public String home(){
        return "/aboutUs/home";
    }

}
