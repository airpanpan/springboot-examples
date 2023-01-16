package com.example.flowable;

import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Lists;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.*;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .addClasspathResource("holidayRequestFour.bpmn20.xml")
                .name("请求流程4")
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

        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId("47501").singleResult();
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getName() = " + processDefinition.getName());
        System.out.println("processDefinition.getKey() = " + processDefinition.getKey());
        System.out.println("processDefinition.getId() = " + processDefinition.getId());

      /*  DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        //Deployment deployment = deploymentQuery.deploymentName("请求流程").singleResult();
        Deployment deployment = deploymentQuery.deploymentId("2501").singleResult();
        System.out.println("deployment.getName() = " + deployment.getName());
        System.out.println("deployment.getId() = " + deployment.getId());
        System.out.println("deployment.getKey() = " + deployment.getKey());*/
    }


    /**
     * 删除流程
     */
    @Test
    public void testDeleteDeploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除部署流程,如果部署的流程启动了，则不允许删除
        //repositoryService.deleteDeployment("9c9266bd-90af-11ed-9650-dc215cb1c48e");
        //删除部署流程，cascade为ture时，流程启动也能删除，相关任务一并删除
        repositoryService.deleteDeployment("7501", Boolean.TRUE);

    }


    /**
     * 生成流程图
     * @throws Exception
     */
    @Test
    public void genProcessDiagram() throws Exception {
        //获取流程图

        RepositoryService repositoryService = processEngine.getRepositoryService();
        BpmnModel bpmnModel = repositoryService.getBpmnModel("holidayRequestTwo:1:25004");
        ProcessEngineConfiguration engineConf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engineConf.getProcessDiagramGenerator();


        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("holidayRequestTwo:1:25004")
                //.processInstanceId("20001")
                .finished() //查询历史记录完成的
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();

        /**
         * 选择流程图的高亮连线
         */
        List<String> flows = new ArrayList<>();
        List<String> activityIds = list.stream().map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());
        for (HistoricActivityInstance instance : list) {
            if (instance.getActivityType().equals("sequenceFlow")){
                flows.add(instance.getActivityId());
            }
        }
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png",
                activityIds,
                flows,
                "宋体",
                "宋体",
                "宋体",
                engineConf.getClassLoader(),
                1.0,true);
        try {
            OutputStream out = null;
            byte[] buf = new byte[1024];
            FileOutputStream fos = new FileOutputStream("D://img/test3.png");
            int len = 0; //每次读取数据的长度
            while( (len = in.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            in.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取部署的流程，输出bpmn20.xml格式
     */
    @Test
    public void getProcessModelToBPMN20(){
        //获取服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream in = repositoryService.getProcessModel("holidayRequestTwo:1:25004");
        try {
            OutputStream out = null;
            byte[] buf = new byte[1024];
            FileOutputStream fos = new FileOutputStream("D://img/processModel.bpmn20.xml");
            int len = 0; //每次读取数据的长度
            while( (len = in.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            in.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取每个节点的坐标和任务信息
     * 用于流程图中，前端根据某坐标拉取该节点的审批详情
     */
    @Test
    public void getActivityNodeInfo(){
        //获取服务,该服务管理与控制部署(deployments)与流程定义(process definitions)的操作
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //
        BpmnModel bpmnModel = repositoryService.getBpmnModel("holidayRequestTwo:1:25004");
        //获取
        List<Process> processes = bpmnModel.getProcesses();
        List<UserTask> userTasks = Lists.newArrayList();
        for (Process process : processes) {
            userTasks.addAll(process.findFlowElementsOfType(UserTask.class));
        }

        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionId("holidayRequestTwo:1:25004")
                .finished() //查询历史记录完成的
                .list();

        List<Map<String , String >> maps = Lists.newArrayList();
        for (UserTask userTask : userTasks) {
            Map<Object, Object> map = new HashMap<>();
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(userTask.getId());
            System.out.println("graphicInfo.getX() = " + graphicInfo.getX());
            System.out.println("graphicInfo.getY() = " + graphicInfo.getY());
            System.out.println("graphicInfo.getHeight() = " + graphicInfo.getHeight());
            System.out.println("graphicInfo.getWidth() = " + graphicInfo.getWidth());
            System.out.println("JSONObject.toJSONString(graphicInfo) = " + JSONObject.toJSONString(graphicInfo));
            for (HistoricTaskInstance taskInstance : list) {
                System.out.println("taskInstance.getCreateTime() = " + taskInstance.getCreateTime());
                System.out.println("taskInstance.getEndTime() = " + taskInstance.getEndTime());
                System.out.println("taskInstance.getAssignee() = " + taskInstance.getAssignee());
                System.out.println("taskInstance.getDueDate() = " + taskInstance.getDueDate());
            }
        }
    }



}
