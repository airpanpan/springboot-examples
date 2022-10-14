package com.example.flowable.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HistanceInstanceEntity {

    String processInstanceId;
    String taskId;
    Date startTime;
    Date endTime;
}
