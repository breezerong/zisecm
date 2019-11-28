package com.ecm.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;

public class SendRejectionMail {
	public void execute(DelegateExecution execution) {
			System.out.println("SendRejectionMail "+ execution.getVariable("employee"));
		}
}
