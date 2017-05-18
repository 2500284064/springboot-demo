package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * Created by lenovo on 2017/5/18.
 */
@Configuration
public class ThymeleafConfig {
    @Bean(name = "thymeleafResolver")
    public ThymeleafViewResolver thymeleafViewResolver(){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        return resolver;
    }
}
