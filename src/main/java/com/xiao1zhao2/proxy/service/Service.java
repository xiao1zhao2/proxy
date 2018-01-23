package com.xiao1zhao2.proxy.service;

public class Service implements IService {

	@Override
	public Boolean service(String name, int age) throws Exception {
		System.out.println("hello :" + name + ",age:" + (100 / age));
		Thread.sleep(100);
		return true;
	}

}
