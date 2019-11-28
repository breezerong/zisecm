package com.ecm.flowable.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 9:35 2018/4/25
 * @Modified By:
 */
public class JobListener implements FlowableEventListener {
    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        switch ((FlowableEngineEventType)flowableEvent.getType()) {

            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job well done!");
                break;

            case JOB_EXECUTION_FAILURE:
                System.out.println("A job has failed...");
                break;
        }    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

	@Override
	public String getOnTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFireOnTransactionLifecycleEvent() {
		// TODO Auto-generated method stub
		return false;
	}
}
