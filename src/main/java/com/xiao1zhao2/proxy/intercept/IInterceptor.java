package com.xiao1zhao2.proxy.intercept;

public interface IInterceptor {

	Object before(InterceptContext context) throws Exception;

	Object after(InterceptContext context) throws Exception;

	Object excep(InterceptContext context) throws Exception;
}
