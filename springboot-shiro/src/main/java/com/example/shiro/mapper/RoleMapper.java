package com.example.shiro.mapper;

import com.example.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RoleMapper {

    @Select( "SELECT id,name FROM role  WHERE user_id=${userId}" )
    List<Role> getRolesByUserId(@Param("userId") Long userId);

}
