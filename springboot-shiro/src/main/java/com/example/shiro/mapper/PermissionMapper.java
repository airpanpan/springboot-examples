package com.example.shiro.mapper;

import com.example.shiro.entity.RolePermisson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PermissionMapper {

    @Select( "SELECT A.NAME AS roleName,C.url FROM role AS A LEFT JOIN role_permission B ON A.id=B.role_id LEFT JOIN permission AS C ON B.permission_id=C.id" )
    List<RolePermisson> getRolePermissions();

    @Select( "SELECT * FROM permission where role_name =  #{roleName}" )
    List<RolePermisson> getRolePermissionsByRoleName(@Param("roleName") String roleName);
}
