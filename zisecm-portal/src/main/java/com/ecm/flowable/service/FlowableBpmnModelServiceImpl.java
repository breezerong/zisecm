package com.ecm.flowable.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: : FlowableBpmnModelServiceImpl
 * @projectName : flowable
 * @description: BpmnModel service
 */
@Service
public class FlowableBpmnModelServiceImpl  implements IFlowableBpmnModelService {
    @Autowired
    private RepositoryService repositoryService;
    
    public BpmnModel getBpmnModelByProcessDefId(String processDefId) {
        return repositoryService.getBpmnModel(processDefId);
    }

    public List<FlowNode> findFlowNodes(String processDefId) {
        List<FlowNode> flowNodes = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> list = process.getFlowElements();
        list.forEach(flowElement -> {
            if (flowElement instanceof FlowNode) {
                flowNodes.add((FlowNode) flowElement);
            }
        });
        return flowNodes;
    }

    @Override
    public List<EndEvent> findEndFlowElement(String processDefId) {
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        if (bpmnModel != null) {
            Process process = bpmnModel.getMainProcess();
            return process.findFlowElementsOfType(EndEvent.class);
        } else {
            return null;
        }
    }
    @Override
    public FlowNode findMainProcessActivityByActivityId(String processDefId, String activityId) {
        FlowNode activity = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        Process process = bpmnModel.getMainProcess();
        FlowElement flowElement = process.getFlowElement(activityId);
        if (flowElement != null) {
            activity = (FlowNode) flowElement;
        }
        return activity;
    }

    @Override
    public FlowNode findFlowNodeByActivityId(String processDefId, String activityId) {
        FlowNode activity = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes){
            FlowElement flowElement = process.getFlowElementMap().get(activityId);
            if (flowElement != null) {
                activity = (FlowNode) flowElement;
                break;
            }
        }
        return activity;
    }
    @Override
    public boolean checkActivitySubprocessByActivityId(String processDefId, String activityId) {
        boolean flag = true;
        List<FlowNode> activities = this.findFlowNodesByActivityId(processDefId,activityId);
        if (CollectionUtils.isNotEmpty(activities)){
            flag = false;
        }
        return flag;
    }

    public List<FlowNode> findFlowNodesByActivityId(String processDefId, String activityId) {
        List<FlowNode> activities = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            FlowElement flowElement = process.getFlowElement(activityId);
            if (flowElement != null) {
                FlowNode flowNode = (FlowNode) flowElement;
                activities.add(flowNode);
            }
        }
        return activities;
    }

    @Override
    public List<Activity> findActivityByActivityId(String processDefId, String activityId) {
        List<Activity> activities = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            FlowElement flowElement = process.getFlowElement(activityId);
            if (flowElement != null) {
                Activity activity = (Activity) flowElement;
                activities.add(activity);
            }
        }
        return activities;
    }

    @Override
    public Activity findActivityByName(String processDefId, String name) {
        Activity activity = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> list = process.getFlowElements();
        for (FlowElement f : list) {
            if (StringUtils.isNotBlank(name)) {
                if (name.equals(f.getName())) {
                    activity = (Activity) f;
                    break;
                }
            }
        }
        return activity;
    }
}
