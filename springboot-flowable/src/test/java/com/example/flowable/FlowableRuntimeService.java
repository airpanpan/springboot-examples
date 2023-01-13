package com.example.flowable;

import com.google.common.collect.Maps;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
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
        variables.put("employee", "zhangsan");
        variables.put("nrOfHolidays", "5day");
        variables.put("description", "张三请求5天");
        //
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holidayRequestTwo", variables);
        System.out.println("instance.getProcessDefinitionId() = " + instance.getProcessDefinitionId());
        System.out.println("instance.getProcessDefinitionKey() = " + instance.getProcessDefinitionKey());
        System.out.println("instance.getId() = " + instance.getId());
        System.out.println("instance.getActivityId() = " + instance.getActivityId());
    }


    @Test
    public void testQueryTask(){
        //获取runtimeservice
        TaskService taskService = processEngine.getTaskService();
        //根据流程key查询任务
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("holidayRequestTwo").taskAssignee("zhangsan").list();
        for (Task task : tasks){
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getDescription() = " + task.getDescription());
            System.out.println("task.getId() = " + task.getId());
        }
    }

    @Test
    public void testCompleteTask(){
        //获取runtimeservice
        TaskService taskService = processEngine.getTaskService();
        //根据流程key查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequestTwo")
                .taskAssignee("zhangsan").singleResult();
        Map<String, Object> map = Maps.newHashMap();
        map.put("approved", false);
        //完成任务
        taskService.complete(task.getId(), map);
    }



}
