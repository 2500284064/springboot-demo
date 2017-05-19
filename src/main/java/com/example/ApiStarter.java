package com.example;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example")    //此时由于主类在根目录下，所以默认的扫描目录即为com.example目录
//上面三个注解可用@SpringBootApplication代替

//@MapperScan(value = "com.example.db.mapper")        此注解被MapperScannerConfig类替代
@EnableTransactionManagement(proxyTargetClass = true)
public class ApiStarter {

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