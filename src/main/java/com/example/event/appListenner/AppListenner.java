package com.example.event.appListenner;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by lenovo on 2017/6/13.
 */

//application事件监听器,
public class AppListenner implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
//        System.out.println("----------" + event.toString());
    }
}
