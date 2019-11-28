package com.ecm.flowable.listener;

import java.util.HashMap;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 10:14 2018/4/25
 * @Modified By:
 */
public class StartExecutorListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
    	//查询借阅流程中的表单变量
    	//添加业务逻辑解析表单或变量，执行判断操作
    	//如果纸质节约且内部公开，设置input=纸质借阅&内部公开 否则设置 input= 否
    	//delegateExecution.getEventName()
    	//delegateExecution.setTransientVariable("input","否");
        HashMap<String, Object> map = new HashMap<>();
        map.put("input", "纸质借阅&内部公开");
        map.put("Task_review", "reject");
        
        //map 需要从借阅表单ecm.document.type_name="借阅单" +  流程记录里取
        //流程记录中记录了表单ID
        //
 
    	String  process_name=delegateExecution.getProcessDefinitionId().split(":")[0];
    	if("process_borrow".equals(process_name)) {//借阅流程
    		if("exclusivegateway_paperAndOpen".equals(delegateExecution.getCurrentActivityId())) {
            	delegateExecution.setTransientVariables(map);
    		}
    	}
    	
    }
}
