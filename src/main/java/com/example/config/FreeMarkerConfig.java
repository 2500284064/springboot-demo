package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by lenovo on 2017/6/14.
 */
@Configuration
public class FreeMarkerConfig {

    @Bean(name = "freeMarkerResolver")
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setOrder(4);
        return viewResolver;
    }
}
