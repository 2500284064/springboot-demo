package com.example;

import com.example.event.appListenner.AppListenner;
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
@EnableTransactionManagement(proxyTargetClass = true)  //支持事务管理
public class ApiStarter {

    public static void main(String[] args) throws Exception {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication app = new SpringApplication(ApiStarter.class);
        app.setBannerMode(Banner.Mode.CONSOLE);          //banner
        /*设置web环境是否为真,这将影响SpringApplication设置上下文*/
//        app.setWebEnvironment(false);
        /*设置上下文类型*/
//        app.setApplicationContextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
//        app.setApplicationContextClass(AnnotationConfigApplicationContext.class);

        /*添加Application事件监听器， 在上下文创建之前即可注册*/
        app.addListeners(new AppListenner());

        /*禁止命令行添加属性*/
        app.setAddCommandLineProperties(false);

        app.run(args);
    }

}