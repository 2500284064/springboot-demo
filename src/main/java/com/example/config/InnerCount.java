package com.example.config;

import com.example.db.entity.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2017/6/13.
 */

/*绑定自定义属性*/
@Component
@ConfigurationProperties(prefix = "innercount")
public class InnerCount {

//    private User innerUser;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public User getInnerUser() {
//        innerUser = new User();
//        innerUser.setUserName(this.getUsername());
//        innerUser.setPassword(this.getPassword());
//        return innerUser;
//    }
//
//    public void setInnerUser(User innerUser) {
//        this.innerUser = innerUser;
//    }
}
