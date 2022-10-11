package com.example.shiro.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.shiro.entity.User;
import com.example.shiro.service.IUserService;
import com.example.shiro.utils.DigestsUtil;
import com.example.shiro.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("mainToken")
public class MainTokenController {

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

    @Autowired
    private IUserService userService;



    @RequestMapping("/login")
    @GetMapping
    public Map<String, String> login(@RequestParam("username") String username,@RequestParam("password") String password) {
       /* Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));*/
        User user = userService.loadUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("账号/密码有误");
        }
        String pwd = DigestsUtil.md5(password, user.getUsername()); //用户名作为盐值
        if (!user.getPassword().equals(pwd)){
            throw new IllegalArgumentException("密码错误");
        }
        String token = getToken(user);
        redisTemplate.opsForValue().set("token:"+token, JSONObject.toJSONString(user), 1800, TimeUnit.SECONDS);
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return result;
    }

    @RequestMapping("/getUserInfoAdmin")
    @GetMapping
    @RequiresRoles("admin")
    @RequiresPermissions("admin:query")
    public Map<String, String> getUserInfoAdmin(@RequestParam("username") String username) {
        User user = userService.loadUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("账号/密码有误");
        }

        Map<String, String> result = new HashMap<>();
        result.put("userName", user.getUsername());
        return result;
    }

    @RequestMapping("/getUserInfoUser")
    @GetMapping
    @RequiresRoles("user")
    @RequiresPermissions("user:query")
    public Map<String, String> getUserInfoUser(@RequestParam("username") String username) {
        User user = userService.loadUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("账号/密码有误");
        }

        Map<String, String> result = new HashMap<>();
        result.put("userName", user.getUsername());
        return result;
    }

    @RequestMapping("/loginout")
    @GetMapping
    public Boolean loginout(HttpServletRequest request) {
        try {
            redisTemplate.delete("token:" + request.getHeader("token"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    /**
     * 生成token
     *
     * @return
     */
    private String getToken(User user) {
        //return UUID.randomUUID().toString().replace("-", "");
        //采用JWT加密
        return JWTUtil.generateToken(user.getId(), user);
    }




}
