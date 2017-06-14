package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by lenovo on 2017/6/14.
 */
@Configuration
@EnableSwagger2
@Profile("test")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("ftz", "http://swagger.io/", "2500284064@qq.com");
        return new ApiInfoBuilder()
                .title("springboot-demo Restful APIs")
                .description("springboot-demo 接口文档")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
