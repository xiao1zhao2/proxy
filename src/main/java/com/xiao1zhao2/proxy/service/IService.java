package com.xiao1zhao2.proxy.service;

import com.xiao1zhao2.proxy.annotation.IIntercept;
import com.xiao1zhao2.proxy.intercept.DefaultInterceptor;

@IIntercept(intercept = DefaultInterceptor.class)
public interface IService {

	Boolean service(String name, int age) throws Exception;
}
