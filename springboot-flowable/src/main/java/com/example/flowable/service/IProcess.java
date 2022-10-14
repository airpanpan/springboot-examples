package com.example.flowable.service;

import com.example.flowable.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IProcess {

    /**
     * 创建一个流程实例，创建实例时会登记一些信息，这些信息可以通过调用
     * queryProcessVariables方法获取到，调用时需要传递processInstanceId
     * @param paramObj
     * @return
     */
    ProcessInstanceEntity startProcess(ParamObj paramObj);

    /**
     * 获取指定工作人的代办任务
     * @param assignee
     * @return
     */
    List<TaskInstanceEntity> taskInstance(String assignee);

    /**
     * 处理工作
     * @param paramObj
     */
    void handleTask(ParamObj paramObj);

    /**
     * 获取某个流程实体的状态，各个审批环节所处的状态信息
     * @param processInstanceId
     * @return
     */
    List<ProcessStatusEntity> queryProcessStatus(String processInstanceId);

    /**
     * 查看创建流程实例时登记的变量信息
     * @param processInstanceId
     * @return
     */
    Map<String,Object> queryProcessVariables(String processInstanceId);

    /**
     * 获取某人的历史审批数据
     * @param assignee
     * @return
     */
    List<HistanceInstanceEntity> queryHistoryProcess(String assignee);

    /**
     * 生成流程的图谱
     * @param httpServletResponse
     * @param processInstanceId
     */
    void genProcessDiagram(HttpServletResponse httpServletResponse, String processInstanceId) throws Exception;

    /**
     * 查询是否存在历史数据的流程实例
     * @param processInstanceId
     * @return
     */
    boolean isExistHistoricProcessInstance(String processInstanceId);

    /**
     * 查询指定的流程是否是运行中的流程
     * @param processInstanceId
     * @return
     */
    boolean isExistRunningProcessInstance(String processInstanceId);

    /**
     * 将指定的流程挂起
     * @param processInstanceId
     */
    void suspendProcessInstance(String processInstanceId);

    /**
     * 终止项目流程
     * @param paramObj
     */
    void terminateProcessInstance(ParamObj paramObj);

    /**
     * 将指定的流程激活
     * @param processInstanceId
     */
    void activateProcessInstance(String processInstanceId);

    /**
     * 删除流程实例
     * @param paramObj
     */
    void deleteProcessInstance(ParamObj paramObj);

    /**
     * 将任务返回到某一步骤
     * @param taskId
     * @param targetTaskKey 返回到的目标任务ID
     */
    void rollbackTask(String taskId, String targetTaskKey);

    boolean isProcessFinished(String processInstanceId);
}
