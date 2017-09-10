package com.iec.core.app.schedule;

import org.springframework.stereotype.Component;


@Component("testScheduler")
public class TestScheduler extends AbsJobScheduler{

	@Override
	protected void schedule() {
		System.out.println("this is a test scheduler。。。。。。");

	}
}
