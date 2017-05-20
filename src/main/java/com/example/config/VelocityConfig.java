package com.example.config;

import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * Created by lenovo on 2017/5/16.
 *
 * 配置布局，默认布局文件夹为layout， 且若不使用VelocityProperties 参数则无法使用布局。
 */
@Configuration
public class VelocityConfig {
    /*name property is chooseable*/
    @Bean(name = "velocityResolver")
    public VelocityLayoutViewResolver velocityLayoutViewResolver(VelocityProperties properties){
        VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
        properties.applyToViewResolver(resolver);
        resolver.setLayoutUrl("layout/default.vm");
        return resolver;
    }

}
