package com.example.flowable.entity;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class ParamObj {

    //startProcess--the following 5 params
    //@NotNull(message = "resourceId 不能为空")
    String resourceId;
    String requestUser;
    String juniorAdmin;
    String seniorAdmin;
    String assignee;
    //handleTask--the following 3 params
    String comment;
    boolean approved;
    String taskId;
    //delete/get processInstance
    String processInstanceId;
    String deleteReason;

    //rollback
    String targetTaskKey;
}
