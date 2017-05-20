package com.example.shiro;

import com.example.db.entity.Right;
import com.example.db.entity.Role;
import com.example.db.entity.User;
import com.example.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/5/20.
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    private static final String RIGHT_TYPE_MENU = "1";
    private static final String RIGHT_TYPE_OPERATE = "2";

    @Autowired
    private UserService userService;

    /*首先执行该登录认证*/
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken){

        UsernamePasswordToken token = (UsernamePasswordToken) aToken;

        User user = userService.selectUserByUserName(token.getUsername());
        if(user != null){

            //将查询到的用户和密码存放到 authenticationInfo用于后面的权限判断。第三个参数随便。
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), "realmName") ;
            return authenticationInfo ;
        }
        return null;
    }

    /*权限认证*/
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal){

        User user = (User) principal.getPrimaryPrincipal();
        logger.debug("userName = " + user.getUserName());

        if(StringUtils.isEmpty(user.getId())) return null;

        /*添加角色和权限*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<Role> roles = userService.selectRolesByUserId(user.getId());
        List<Right> rights = userService.selectRightsByUserId(user.getId());

        info.setRoles(roles.stream().map( a -> a.getRoleName()).collect(Collectors.toSet()));
        info.setStringPermissions(rights.stream().map( a -> {
            if(RIGHT_TYPE_MENU.equals(a.getType())) return a.getUrl();
            return a.getPermission();
        }).collect(Collectors.toSet()));

        return info;
    }
}
