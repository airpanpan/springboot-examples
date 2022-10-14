package com.example.flowable.entity;

import lombok.Data;

@Data
public class ProcessInstanceEntity {

    String processInstanceId;
    String processDeploymentId;
    String activityId;
}
