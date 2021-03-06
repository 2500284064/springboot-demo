package com.example.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lenovo on 2017/5/16.
 */
@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(){
//        return "login";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login( javax.servlet.http.HttpServletRequest request) {
        WebUtils.getAndClearSavedRequest(request);
        if(SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "/login";
    }

    @RequestMapping(value = "/")
    public String root(){
        return "redirect:/index";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/thymeleaf")
    public String thymeleaf(){
        return "thymeleafIndex";
    }

    //此URL访问权限放开，免认证。
   /* @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(String username, String password){
        //下面为手动登录采取的方法
        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password) ;
        if(innerCount != null){
            token = new UsernamePasswordToken(innerCount.getUsername(), innerCount.getPassword());
        }
        try {
            subject.login(token);
            return "redirect:/index" ;
        }catch (Exception e) {
            return "login";
        }finally {
            return "login";
        }
    }*/

    //login的实现不需要自己实现，在ShiroConfig中的Shiro过滤器声明登录URL，则会在此url被请求时，
    //自动获取 username 和 password 参数，作为登录账号和密码
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signInBak(HttpServletRequest request, ModelMap model){
        String errorClassName = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        logger.info("errorClassName {}", errorClassName);

        if(errorClassName != null) {
            if (errorClassName.endsWith("UnknownAccountException") ||
                    errorClassName.endsWith("IncorrectCredentialsException")) {
                model.addAttribute("errorMsg", "账号或密码错误");
            } else {
                model.addAttribute("errorMsg", "未知错误,请联系管理员.");
            }
        }
        return "/login";
    }

}
