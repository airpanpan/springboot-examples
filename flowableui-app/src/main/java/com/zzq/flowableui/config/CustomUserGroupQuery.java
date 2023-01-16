package com.zzq.flowableui.config;

import com.google.common.collect.Lists;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.util.List;

/**
 * 参考LDAP方式，自定义用户、组
 *
 */
public class CustomUserGroupQuery extends GroupQueryImpl {


    @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<Group> executeQuery() {
        /*if (getUserId() != null) {
            return findGroupsByUser(getUserId());
        } else if (getId() != null) {
            return findGroupsById(getId());
        } else {
            return findAllGroups();
        }*/
        GroupEntity group = new GroupEntityImpl();
        group.setName("经理");
        group.setType("MANAGER");
        group.setId("manager");
        return Lists.newArrayList(group);
    }

    protected List<Group> findGroupsByUser(String userId) {

      /*  if (ldapGroupCache != null) {
            List<Group> groups = ldapGroupCache.get(userId);
            if (groups != null) {
                return groups;
            }
        }

        String searchExpression = ldapConfigurator.getLdapQueryBuilder().buildQueryGroupsForUser(ldapConfigurator, userId);
        List<Group> groups = executeGroupQuery(searchExpression);

        // Cache results for later
        if (ldapGroupCache != null) {
            ldapGroupCache.add(userId, groups);
        }*/
        GroupEntity group = new GroupEntityImpl();
        return Lists.newArrayList(group);
    }


    protected List<Group> findAllGroups() {
        GroupEntity group = new GroupEntityImpl();
        return Lists.newArrayList(group);
    }


}
