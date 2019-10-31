package com.ecm.core.service.statusflow;

public interface ILifeCycleEvent {
	/**
	 * 事件扩展方法，
	 * @return 返回true代表执行成功，返回false代表执行失败
	 * @throws Exception
	 */
	public boolean execute() throws Exception;
}
