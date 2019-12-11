package org.zisecm.jobs;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.err.println("-------延迟5000毫秒，每1000毫秒执行一次--------");
            }
        }, 5000, 1000);
    }
}
