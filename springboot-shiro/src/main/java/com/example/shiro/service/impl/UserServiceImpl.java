package com.example.shiro.service.impl;

import com.example.shiro.entity.Role;
import com.example.shiro.entity.RolePermisson;
import com.example.shiro.entity.User;
import com.example.shiro.mapper.PermissionMapper;
import com.example.shiro.mapper.RoleMapper;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    private PermissionMapper permissionMapper;

    @Override
    public User loadUserByUsername(String username) {
        User user = userMapper.loadUserByUsername(username);
        return user;
    }

    @Override
    public List<Role> queryRolesByUserId(Long userId) {
        List<Role> rolesByUserId = roleMapper.getRolesByUserId(userId);
        return rolesByUserId;
    }

    @Override
    public List<RolePermisson> queryPermsByRoleId(String roleName) {
        List<RolePermisson> rolePermissionsByRoleName = permissionMapper.getRolePermissionsByRoleName(roleName);
        return rolePermissionsByRoleName;
    }
}
