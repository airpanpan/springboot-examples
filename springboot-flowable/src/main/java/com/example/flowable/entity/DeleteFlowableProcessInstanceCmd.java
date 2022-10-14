package com.example.flowable.entity;

import lombok.Data;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.compatibility.Flowable5CompatibilityHandler;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.Flowable5Util;

import java.io.Serializable;

@Data
public class DeleteFlowableProcessInstanceCmd implements Command<Void>, Serializable {

    String processInstanceId;
    String deleteReason;
    //是否删除历史
    boolean cascade=true;

    public DeleteFlowableProcessInstanceCmd(){

    }

    public DeleteFlowableProcessInstanceCmd(String processInstanceId, String deleteReason){
        this.deleteReason=deleteReason;
        this.processInstanceId=processInstanceId;
    }

    public DeleteFlowableProcessInstanceCmd(String processInstanceId,
                                            String deleteReason,
                                            boolean cascade){
        this.deleteReason=deleteReason;
        this.processInstanceId=processInstanceId;
        this.cascade=cascade;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntity entity= CommandContextUtil.getExecutionEntityManager(commandContext)
                .findById(processInstanceId);
        if(entity!=null){
            if(entity.isDeleted()){
                return null;
            }
            if(Flowable5Util.isFlowable5ProcessDefinitionId(commandContext,entity.getProcessDefinitionId())){
                Flowable5CompatibilityHandler handler=Flowable5Util.getFlowable5CompatibilityHandler();
                handler.deleteProcessInstance(processInstanceId,deleteReason);
            }else{
                CommandContextUtil.getExecutionEntityManager(commandContext).deleteProcessInstance(entity.getProcessInstanceId(),deleteReason,cascade);
            }
        }
        return null;
    }
}
