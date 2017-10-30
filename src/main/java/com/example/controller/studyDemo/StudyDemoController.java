package com.example.controller.studyDemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 2017/10/27.
 */
@Controller
@RequestMapping("/studyDemo")
public class StudyDemoController {

    @RequestMapping("/home")
    public String home(){
        return "/studyDemo/home";
    }

}
