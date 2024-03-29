package com.example.shiro.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

@Controller
public class MainController {

  /*  @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }*/

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:toLogin";
    }

    @RequestMapping("/index")
    @RequiresRoles(value = {"user","admin"}, logical = Logical.OR)
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));
        return "redirect:index";
    }



    @RequestMapping("/toLogin")
    public String toLogin() {
        //这边我们,默认是返到templates下的login.html
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute( "loginError"  , true);
        return "login";
    }

    @GetMapping("/401")
    public String accessDenied() {
        return "401";
    }

    @GetMapping("/user/common")
    @RequiresRoles(value = {"user","admin"}, logical = Logical.OR)
    public String common() {
        return "user/common";
    }


    @GetMapping("/user/admin")
    @RequiresRoles(value = {"admin"})
    public String admin() {
        return "user/admin";
    }


}
