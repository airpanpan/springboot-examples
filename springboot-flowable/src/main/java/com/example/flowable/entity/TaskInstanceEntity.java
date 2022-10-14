package com.example.flowable.entity;

import lombok.Data;

import java.util.Date;
@Data
public class TaskInstanceEntity {

    private String taskId;
    private String taskName;
    private String processInstanceId;
    private String requestUser;
    private String resourceId;
    private Date createTime;
}
