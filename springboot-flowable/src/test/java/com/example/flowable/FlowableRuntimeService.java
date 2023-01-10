package com.example.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class FlowableRuntimeService {


    private ProcessEngine processEngine;


    /**
     * 初始化
     */
    @BeforeEach
    public void before(){
        //创建Flowable流程引擎
        //ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        //配置JDBC
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("root");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable_test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        //如果数据库中不存在表结构，则做新建操作
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //构建flowable流程引擎
        processEngine = configuration.buildProcessEngine();
    }

    @Test
    public void testRuntimeProcess(){
        //获取runtimeservice
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", "5day");
        variables.put("description", "张三请求5天");
        //
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        System.out.println("instance.getProcessDefinitionId() = " + instance.getProcessDefinitionId());
        System.out.println("instance.getProcessDefinitionKey() = " + instance.getProcessDefinitionKey());
        System.out.println("instance.getId() = " + instance.getId());
        System.out.println("instance.getActivityId() = " + instance.getActivityId());
    }


}
