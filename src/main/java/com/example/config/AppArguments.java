package com.example.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lenovo on 2017/6/13.
 */

/*访问应用参数*/
/*顺序：在上下文创建之后，refreshed之前 */
@Component
public class AppArguments {

    @Autowired
    public AppArguments(ApplicationArguments args){
        boolean debug = args.containsOption("debug");
        List<String> list = args.getNonOptionArgs();

        System.out.println("-----Args: " + debug + "; " + StringUtils.join(list, ","));
    }
}
