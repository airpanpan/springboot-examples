package com.example.flowable.service.impl;

import com.example.flowable.entity.*;
import com.example.flowable.service.IProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
public class IProcessImpl implements IProcess {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    ManagementService managementService;

    @Override
    public ProcessInstanceEntity startProcess(ParamObj paramObj) {
        Map<String, Object> variables = new HashMap<>();
        // 请求的资源ID
        variables.put("resourceId", paramObj.getResourceId());
        // 请求发起用户
        variables.put("requestUser", paramObj.getRequestUser());
        // 初级审批用户
        variables.put("juniorAdmin", paramObj.getJuniorAdmin());
        // 高级审批用户
        variables.put("seniorAdmin", paramObj.getSeniorAdmin());
        ProcessInstance processInstance=runtimeService.
                startProcessInstanceByKey("processTest", variables);
        ProcessInstanceEntity entity=new ProcessInstanceEntity();
        entity.setProcessDeploymentId(processInstance.getDeploymentId());
        entity.setProcessInstanceId(processInstance.getProcessInstanceId());
        entity.setActivityId(processInstance.getActivityId());
        return entity;
    }

    @Override
    public List<TaskInstanceEntity> taskInstance(String assignee) {
        List<TaskInstanceEntity> entities=new ArrayList<>();
        List<Task> tasks= taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();
        if(!CollectionUtils.isEmpty(tasks)){
            tasks.stream().forEach(task -> {
                TaskInstanceEntity entity=new TaskInstanceEntity();
                String id=task.getId();
                entity.setCreateTime(task.getCreateTime());
                entity.setTaskName(task.getName());
                entity.setProcessInstanceId(task.getProcessInstanceId());
                entity.setTaskId(id);
                Map<String, Object> processVariables = taskService.getVariables(id);
                entity.setRequestUser(processVariables.get("requestUser").toString());
                entity.setResourceId(processVariables.get("resourceId").toString());
                entities.add(entity);
            });
        }
        return entities;
    }

    @Override
    public void handleTask(ParamObj paramObj) {
        Map<String, Object> taskVariables = new HashMap<>();
        String approved=paramObj.isApproved()?"Y":"N";
        taskVariables.put("approved", approved);
        //审核结果和审核意见都封装为JSON然后放在评论里，后续需要进行逆操作。
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map= new HashMap<>();
        map.put("approved", approved);
        map.put("comment", paramObj.getComment());
        try {
            String json = objectMapper.writeValueAsString(map);
            taskService.addComment(paramObj.getTaskId(), null, json);
            taskService.complete(paramObj.getTaskId(), taskVariables);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProcessStatusEntity> queryProcessStatus(String processInstanceId) {
        List<ProcessStatusEntity> result = new ArrayList<>();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).list();
        if(CollectionUtils.isEmpty(historicTaskInstances)) {
            throw new RuntimeException("Process instance [" + processInstanceId + "] not exist");
        }
        for (HistoricTaskInstance hti : historicTaskInstances) {
            String taskId = hti.getId();
            String taskName = hti.getName();
            String assignee = hti.getAssignee();
            Date createTime = hti.getCreateTime();
            String comment = null;
            String approved=null;
            List<Comment> comments = taskService.getTaskComments(taskId);
            if (!CollectionUtils.isEmpty(comments)) {
                comment = comments.get(0).getFullMessage();
                if(null!=comment) {
                    //这里进行评论的JSON数据的逆操作提取数据
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map<String,Object> data = mapper.readValue(comment, Map.class);
                        approved=data.get("approved").toString();
                        comment=data.get("comment").toString();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
            ProcessStatusEntity pd=new ProcessStatusEntity();
            pd.setTaskName(taskName);
            pd.setAssignee(assignee);
            pd.setCreateTime(createTime);
            pd.setApproved(approved);
            pd.setComment(comment);
            pd.setTaskId(hti.getId());
            pd.setProcessInstanceId(hti.getProcessInstanceId());
            result.add(pd);
        }
        return result;
    }

    @Override
    public Map<String, Object> queryProcessVariables(String processInstanceId) {
        List<HistoricVariableInstance> historicVariableInstances =
                historyService.createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstanceId).list();
        if (historicVariableInstances == null) {
            throw new RuntimeException("Process instance [" + processInstanceId + "] not exist");
        }
        Map<String,Object> ret= new HashMap<>();
        for(HistoricVariableInstance var: historicVariableInstances) {
            ret.put(var.getVariableName(), var.getValue());
        }
        return ret;
    }

    @Override
    public List<HistanceInstanceEntity> queryHistoryProcess(String assignee) {
        List<HistanceInstanceEntity> result=new ArrayList<>();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee(assignee).finished().orderByHistoricActivityInstanceEndTime().desc().list();
        for(HistoricActivityInstance h : activities) {
            HistanceInstanceEntity d=new HistanceInstanceEntity();
            d.setProcessInstanceId(h.getProcessInstanceId());
            d.setTaskId(h.getTaskId());
            d.setStartTime(h.getStartTime());
            d.setEndTime(h.getEndTime());
            result.add(d);
        }
        return result;
    }

    @Override
    public void genProcessDiagram(HttpServletResponse httpServletResponse,
                                  String processInstanceId) throws Exception{
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().
                processInstanceId(processInstanceId).singleResult();
        //流程走完的不显示图
        if (pi == null) {
//            System.out.println("不存在该流程或则流程已经走完");
            throw new RuntimeException("不存在该流程或则流程已经走完");
//            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engineConf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engineConf.getProcessDiagramGenerator();
       /* InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png",
                activityIds,
                flows,
                engineConf.getActivityFontName(),
                engineConf.getLabelFontName(),
                engineConf.getAnnotationFontName(),
                engineConf.getClassLoader(),
                1.0,true);*/
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png",
                activityIds,
                flows,
                "宋体",
                "宋体",
                "宋体",
                engineConf.getClassLoader(),
                1.0,true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int length = 0;
        try {
            //httpServletResponse.setContentType("image/png;charset=utf-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            out = httpServletResponse.getOutputStream();
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    public boolean isExistHistoricProcessInstance(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().
                        processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isExistRunningProcessInstance(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return false;
        }
        return true;
    }

    @Override
    public void suspendProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    @Override
    public void terminateProcessInstance(ParamObj paramObj) {
        runtimeService.deleteProcessInstance(paramObj.getProcessInstanceId(),paramObj.getDeleteReason());
    }

    @Override
    public void activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    @Override
    public void deleteProcessInstance(ParamObj paramObj) {
        //查询是否操作
        long count = runtimeService.createExecutionQuery().processInstanceId(paramObj.getProcessInstanceId()).count();
        if(count>0){
            DeleteFlowableProcessInstanceCmd cmd=
                    new DeleteFlowableProcessInstanceCmd(paramObj.getProcessInstanceId(),
                            paramObj.getDeleteReason(),true);
            managementService.executeCommand(cmd);
            //runtimeService.deleteProcessInstance(processInstanceId,deleteReason);
        }else{
            //删除历史数据的流程实体
            historyService.deleteHistoricProcessInstance(paramObj.getProcessInstanceId());
        }
    }

    @Override
    public void rollbackTask(String taskId, String targetTaskKey) {
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (currentTask == null) {
            return ;
        }
        List<String> key = new ArrayList<>();
        key.add(currentTask.getTaskDefinitionKey());
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(currentTask.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(key, targetTaskKey)
                .changeState();
    }

    @Override
    public boolean isProcessFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished()
                .processInstanceId(processInstanceId).count()>0;
    }
}
