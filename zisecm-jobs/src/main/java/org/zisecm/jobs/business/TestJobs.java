package org.zisecm.jobs.business;

import org.springframework.stereotype.Service;
import org.zisecm.jobs.core.Ijobs;

@Service
public class TestJobs implements Ijobs{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("I'm a jobs!");
	}

}
