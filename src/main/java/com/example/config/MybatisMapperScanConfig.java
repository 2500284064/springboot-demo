package com.example.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2017/5/19.
 *
 * 配置mapper接口的目录，若application配置文件中未配置mybatis.mapper-location属性，
 * 则默认在相同目录（即可在resources目录下，也可java目录下）下扫描查找mapper.xml文件
 */
@Configuration
public class MybatisMapperScanConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
        mapperScanner.setBasePackage("com.example.db.mapper");
        return mapperScanner;
    }
}
