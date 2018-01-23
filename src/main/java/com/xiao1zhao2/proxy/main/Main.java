package com.xiao1zhao2.proxy.main;

import com.xiao1zhao2.proxy.factory.ProxyFactory;
import com.xiao1zhao2.proxy.service.IService;

public class Main {

	public static void main(String[] args) throws Exception {

		IService service = ProxyFactory.getService(IService.class);
		service.service("zhangsan", 10);
		service.service("lisi", 0);
	}

}
