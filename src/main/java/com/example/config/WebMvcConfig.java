package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lenovo on 2017/6/14.
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /*跨域资源共享支持，从4.2版本开始，SpringMVC对CORS提供开箱即用的支持。只需要在controller方法上注解@CrossOrigin，并添加CORS配置*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }

}
