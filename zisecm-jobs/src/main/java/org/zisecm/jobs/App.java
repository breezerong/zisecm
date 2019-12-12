package org.zisecm.jobs;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.zisecm.jobs.config.JobsConfig;
import org.zisecm.jobs.core.Ijobs;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.product.CacheProduct;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JobsConfig.class,ThymeleafAutoConfiguration.class);
        ctx.refresh();
        CacheProduct c= (CacheProduct) ctx.getBean("cacheProduct");
        try {
			c.run(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	System.out.println("-------延迟5000毫秒，每1000毫秒执行一次--------");
//            	String jobsNameStr = CacheManagerOper.getEcmParameters().get("jobsBeanName").getValue();
//             	String[] jobsNames=jobsNameStr.split(",");
//             	if(jobsNames.length==0) {
//             		return;
//             	}
//             	for(String jobsName : jobsNames) {
             		Thread th=new Thread() {
                	    public void run() {
                	    	Ijobs job= (Ijobs) ctx.getBean("emailService");
                            try {
								job.run();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                	    }
                	};
                	th.start();
//             	}
            	
                
                
            }
        }, 5000, 1000);
    }
}
