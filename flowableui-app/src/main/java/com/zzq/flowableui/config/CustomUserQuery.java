package com.zzq.flowableui.config;

import com.google.common.collect.Lists;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考LDAP方式，自定义用户、组
 *
 */
public class CustomUserQuery extends UserQueryImpl {


    @Override
    public List<User> executeList(CommandContext commandContext) {
        return executeQuery();
    }


   List<User> executeQuery(){

       //查询所有
       UserEntity user = new UserEntityImpl();
       user.setFirstName("Test");
       user.setLastName("Administrator");
       user.setEmail("test-admin@example-domain.tld");
       user.setId("admin");
       user.setPassword("test");


       UserEntity user2 = new UserEntityImpl();
       user2.setFirstName("lisi");
       user2.setLastName("Customer");
       user2.setEmail("test-lisi@example-domain.tld");
       user2.setId("lisi");
       user2.setPassword("test");

       UserEntity user3 = new UserEntityImpl();
       user3.setFirstName("zhangsan");
       user3.setLastName("Customer");
       user3.setEmail("test-lisi@example-domain.tld");
       user3.setId("zhangsan");
       user3.setPassword("test");

       List<UserEntity> list = Lists.newArrayList(user, user2, user3);
       System.out.println("\"调用executeQuery方法\" = " + "调用executeQuery方法");
       if (getId() != null){
           for (UserEntity userEntity : list) {
              if ( userEntity.getId().equals(getId())){
                  return Lists.newArrayList(userEntity);
              }
           }
           return null;
       }else if (getIdIgnoreCase() != null){
           for (UserEntity userEntity : list) {
               if ( getIdIgnoreCase().equals(userEntity.getId())){
                   return Lists.newArrayList(userEntity);
               }
           }
           return null;
       } else if (getFullNameLike() != null){
           return null;
       } else {
/*

           flowable.idm.app.admin.user-id=admin
           flowable.idm.app.admin.password=test
           flowable.idm.app.admin.first-name=Test
           flowable.idm.app.admin.last-name=Administrator
           flowable.idm.app.admin.email=
*/
           //查询所有
           return Lists.newArrayList(user, user2);
       }
   }

    @Override
    public long executeCount(CommandContext commandContext) {
        //查询所有
        UserEntity user = new UserEntityImpl();
        user.setFirstName("Test");
        user.setLastName("Administrator");
        user.setEmail("test-admin@example-domain.tld");
        user.setId("admin");
        user.setPassword("test");


        UserEntity user2 = new UserEntityImpl();
        user2.setFirstName("lisi");
        user2.setLastName("Customer");
        user2.setEmail("test-lisi@example-domain.tld");
        user2.setId("lisi");
        user2.setPassword("test");

        UserEntity user3 = new UserEntityImpl();
        user3.setFirstName("zhangsan");
        user3.setLastName("Customer");
        user3.setEmail("test-lisi@example-domain.tld");
        user3.setId("zhangsan");
        user3.setPassword("test");

        List<UserEntity> list = Lists.newArrayList(user, user2, user3);
        return list.stream().count();
    }

    //public abstract long executeCount(CommandContext commandContext);

}
