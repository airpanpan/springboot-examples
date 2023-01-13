package com.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FlowableHistoryService {

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


    /**
     * 部署流程
     */
    @Test
    public void testHistory(){
        //获取部署流程服务
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("holidayRequest:2:17503")
                //.processInstanceId("20001")
                .finished() //查询历史记录完成的
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        for (HistoricActivityInstance instance : list) {
            System.out.println(instance.getProcessInstanceId() + "-- " + instance.getAssignee() + "--" + instance.getActivityName()
                    + "--" +  instance.getActivityId()
            + "--" + instance.getDurationInMillis() );
        }
    }




}
