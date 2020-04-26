package com.ecm.flowable.method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.flowable.engine.delegate.DelegateExecution;

public class RouteMethod {
    /**
     *
     * <sequenceFlow sourceRef="decision" targetRef="usertask3">
     *        <conditionExpression xsi:type="tFormalExpression">
     *                 <![CDATA[
     *                     ${routeMethod.isPassed(execution)}
     *                 ]]>
     *    </conditionExpression>
     *  </sequenceFlow>
     */

 
    /**
     * 
     */
    public boolean isPassed(DelegateExecution execution){
		Collection<String> variableNames = new ArrayList<String>();
		variableNames.add("nrOfCompletedInstances");
		variableNames.add("nrOfPassedInstances");
		Map<String, Object> varMap = execution.getVariables(variableNames);
		Object nrOfCompletedInstancesObj= varMap.get("nrOfCompletedInstances");
		Object nrOfPassedInstancesObj= varMap.get("nrOfPassedInstances");
		int nrOfCompletedInstances=0;
		int nrOfPassedInstances=0;
		boolean result=false;
		if(nrOfCompletedInstancesObj==null||nrOfPassedInstancesObj==null) {
			result= false;
		}else {
			nrOfCompletedInstances= Integer.valueOf(nrOfCompletedInstancesObj.toString());
			nrOfPassedInstances= Integer.valueOf(nrOfPassedInstancesObj.toString());
			result=nrOfPassedInstances/nrOfCompletedInstances==1;
		}
        return result;
   }
}
