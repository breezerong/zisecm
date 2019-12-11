package org.zisecm.jobs;

import java.util.Timer;
import java.util.TimerTask;

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
        ctx.register(JobsConfig.class);
        ctx.refresh();
        CacheProduct c= (CacheProduct) ctx.getBean("cacheProduct");
        try {
			c.run(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String clauseSql = CacheManagerOper.getEcmParameters().get("InBorrowOrderFile").getValue();
    	System.out.println(clauseSql);
    	Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.err.println("-------延迟5000毫秒，每1000毫秒执行一次--------");
                Ijobs job= (Ijobs) ctx.getBean("testJobs");
                job.run();
            }
        }, 5000, 1000);
    }
}
