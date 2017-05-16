package com.example.config;

import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * Created by lenovo on 2017/5/16.
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
