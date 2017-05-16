package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.PrintStream;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example")    //此时由于主类在根目录下，所以默认的扫描目录即为com.example目录
@RestController
public class ApiStarter {

    @RequestMapping("/")
    String home() {
        return "Hello Worldddd!";
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication app = new SpringApplication(ApiStarter.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        /*设置web环境是否为真*/
//        app.setWebEnvironment(false);
        /*设置上下文类型*/
//        app.setApplicationContextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
        app.run(args);
    }

}