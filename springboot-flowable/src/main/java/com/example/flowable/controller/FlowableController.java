package com.example.flowable.controller;

import com.example.flowable.entity.*;
import com.example.flowable.service.IProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flowableTest")
public class FlowableController {

    @Autowired
    IProcess process;

    @PostMapping("/startProcess")
    public ProcessInstanceEntity startProcess(@RequestBody ParamObj paramObj){
        return process.startProcess(paramObj);
    }

    @GetMapping("/getTaskInstance/{assignee}")
    public List<TaskInstanceEntity> taskInstance(@PathVariable("assignee") String assignee){
        return process.taskInstance(assignee);
    }

    @PutMapping("/handleTask")
    public String handleTask(@RequestBody ParamObj paramObj){
        process.handleTask(paramObj);
        return "success";
    }

    @GetMapping("/queryProcessStatus")
    public List<ProcessStatusEntity> queryProcessStatus(String processInstanceId){
        return process.queryProcessStatus(processInstanceId);
    }

    @GetMapping("/queryProcessVariables")
    public Map<String, Object> queryProcessVariables(String processInstanceId){
        return process.queryProcessVariables(processInstanceId);
    }

    @GetMapping("/queryHistoryProcess")
    public List<HistanceInstanceEntity> queryHistoryProcess(String assignee){
        return process.queryHistoryProcess(assignee);
    }

    @GetMapping("/genProcessDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse,
                                  String processInstanceId) throws Exception {
        process.genProcessDiagram(httpServletResponse,processInstanceId);
    }

    @GetMapping("/isExistHistoricProcessInstance")
    public boolean isExistHistoricProcessInstance(String processInstanceId){
        return process.isExistHistoricProcessInstance(processInstanceId);
    }

    @GetMapping("/isProcessFinished")
    public boolean isProcessFinished(String processInstanceId){
        return process.isProcessFinished(processInstanceId);
    }

    @GetMapping("/isExistRunningProcessInstance")
    public boolean isExistRunningProcessInstance(String processInstanceId){
        return process.isExistRunningProcessInstance(processInstanceId);
    }

    @PutMapping("/suspendProcessInstance")
    public String suspendProcessInstance(String processInstanceId){
        process.suspendProcessInstance(processInstanceId);
        return "流程 "+processInstanceId+" 已经挂起";
    }

    @PutMapping("/terminateProcessInstance")
    public String terminateProcessInstance(ParamObj paramObj){
        process.terminateProcessInstance(paramObj);
        return "流程 "+paramObj.getProcessInstanceId()+" 已经终止";
    }

    @PutMapping("/activateProcessInstance")
    public String activateProcessInstance(String processInstanceId) {
        process.activateProcessInstance(processInstanceId);
        return "流程 "+processInstanceId+" 已经激活";
    }

    @PutMapping("/deleteProcessInstance")
    public String deleteProcessInstance(ParamObj paramObj){
        process.deleteProcessInstance(paramObj);
        return "流程 "+paramObj.getProcessInstanceId()+" 已经删除";
    }

    @PutMapping("/rollback")
    public String rollbackTask(String taskId, String targetTaskKey){
        process.rollbackTask(taskId,targetTaskKey);
        return "流程回退成功";
    }
}
