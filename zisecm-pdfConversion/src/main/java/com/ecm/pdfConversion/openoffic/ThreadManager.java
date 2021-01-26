package com.ecm.pdfConversion.openoffic;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.constraints.Future;

import org.springframework.stereotype.Component;

/**
 * 线程池
 * @author Administrator
 *
 */
@Component(value="threadManager")//交给Spring管理
public class ThreadManager {
	private ExecutorService executorService;
	// private CountDownLatch end;
	 
	/**
	 * 
	 */
	public ThreadManager() {
		executorService = Executors.newFixedThreadPool(10);//初始化10的线程池，当执行的线程超过10，会等待线程池有空位
		
		//end=new CountDownLatch(4);
	}
	
	public void  execute(CountDownLatch end,Runnable runnable){
		executorService.submit(runnable);
		//executorService.shutdown();
	}

}
