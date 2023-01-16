package com.zzq.flowableui.config;


import org.flowable.common.engine.api.FlowableException;
import org.flowable.idm.api.*;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.IdmIdentityServiceImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link} https://www.yisu.com/zixun/566257.html
 * 参考 集成 LDAP 的实现方式
 * 可以覆盖IdmIdentityServiceImpl类，或者直接实现IdmIdentityService接口，并使用实现类作为ProcessEngineConfiguration中的idmIdentityService参数。
    考虑到不需要对 flowable 的 权限进行维护，所以无需实现 新增 和修改方法。
 */
public class CustomIdmIdentityServiceImpl extends IdmIdentityServiceImpl {


    public CustomIdmIdentityServiceImpl(IdmEngineConfiguration idmEngineConfiguration) {
        super(idmEngineConfiguration);
    }

    @Override
    public UserQuery createUserQuery() {
        System.out.println("用户查询--");
        return new CustomUserQuery();
    }

    @Override
    public GroupQuery createGroupQuery() {
        System.out.println("用户组查询--");
        return new CustomUserGroupQuery();
    }

    @Override
    public boolean checkPassword(String userId, String password) {
        return true;
    }

    @Override
    public List<Group> getGroupsWithPrivilege(String name) {
        System.out.println("查询--");
        //throw new FlowableException("LDAP identity service doesn't support creating a new user");
        List<Group> groups = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getGroupId() != null) {
                Group group = new GroupEntityImpl();
                group.setId(privilegeMapping.getGroupId());
                group.setName(privilegeMapping.getGroupId());
                groups.add(group);
            }
        }

        return groups;
    }

    @Override
    public List<User> getUsersWithPrivilege(String name) {
        System.out.println("查询--");
       // throw new FlowableException("LDAP identity service doesn't support creating a new user");
        /**
         * 开放特权.
         */
        List<User> users = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getUserId() != null) {
                User user = new UserEntityImpl();
                user.setId(privilegeMapping.getUserId());
                user.setLastName(privilegeMapping.getUserId());
                users.add(user);
            }
        }

        return users;
    }



    @Override
    public User newUser(String userId) {
        throw new FlowableException("LDAP identity service doesn't support creating a new user");
    }

    @Override
    public void saveUser(User user) {
        throw new FlowableException("LDAP identity service doesn't support saving an user");
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void deleteUser(String userId) {
        throw new FlowableException("LDAP identity service doesn't support deleting an user");
    }

    @Override
    public Group newGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support creating a new group");
    }

    @Override
    public NativeGroupQuery createNativeGroupQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void saveGroup(Group group) {
        throw new FlowableException("LDAP identity service doesn't support saving a group");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support deleting a group");
    }
}
