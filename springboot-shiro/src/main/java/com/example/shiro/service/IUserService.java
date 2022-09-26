package com.example.shiro.service;

import com.example.shiro.entity.Role;
import com.example.shiro.entity.RolePermisson;
import com.example.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {

    User loadUserByUsername(String username);

    List<Role> queryRolesByUserId(Long userId);

    List<RolePermisson> queryPermsByRoleId(String roleName);

}
