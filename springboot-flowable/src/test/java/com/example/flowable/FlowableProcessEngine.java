package com.example.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlowableProcessEngine {

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
    public void testDeploy(){
        //获取部署流程服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //创建一个新的部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/holidayRequest.bpmn20.xml")
                .name("请求流程")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    /**
     * 查询部署的流程
     */
    @Test
    public void testProcessDefinitionQuery(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId("2501").singleResult();
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getName() = " + processDefinition.getName());
        System.out.println("processDefinition.getKey() = " + processDefinition.getKey());
        System.out.println("processDefinition.getId() = " + processDefinition.getId());

        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        //Deployment deployment = deploymentQuery.deploymentName("请求流程").singleResult();
        Deployment deployment = deploymentQuery.deploymentId("2501").singleResult();
        System.out.println("deployment.getName() = " + deployment.getName());
        System.out.println("deployment.getId() = " + deployment.getId());
        System.out.println("deployment.getKey() = " + deployment.getKey());
    }


    /**
     * 删除流程
     */
    @Test
    public void testDeleteDeploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除部署流程,如果部署的流程启动了，则不允许删除
        repositoryService.deleteDeployment("2501");
        //删除部署流程，cascade为ture时，流程启动也能删除，相关任务一并删除
        //repositoryService.deleteDeployment("1", Boolean.TRUE);
    }



}
