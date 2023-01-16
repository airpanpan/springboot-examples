package com.zzq.flowableui;

import org.flowable.engine.IdentityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExampleApplicationTests {

    @Autowired
    private IdentityService identityService;

    @Test
    public void test(){
        //long count = identityService.createUserQuery().userId("1").count();
       // long count2 = identityService.createUserQuery().userId("2").count();
        //System.out.println("count = " + count);
       // System.out.println("count2 = " + count2);
    }
}
