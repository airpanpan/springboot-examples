package com.example.flowable.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessStatusEntity {

    String taskName;
    String taskId;
    String assignee;
    Date createTime;
    String approved;
    String comment;
    String processInstanceId;
    String processDefinitionId;

}
