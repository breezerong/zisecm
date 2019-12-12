package com.ecm.flowable.controller;

import java.util.Map;

import org.flowable.engine.impl.cmd.StartProcessInstanceCmd;

public class StartProcessNameProcessInstanceCmd<T> extends StartProcessInstanceCmd<T> {
    public StartProcessNameProcessInstanceCmd(String processInstanceName, String processDefinitionKey,String businessKey, Map<String, Object> variables, String tenantId) {
        super(processDefinitionKey, null, businessKey, variables,tenantId);
        this.processInstanceName = processInstanceName;
    }
}
