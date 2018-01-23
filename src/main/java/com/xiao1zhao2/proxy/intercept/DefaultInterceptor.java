package com.xiao1zhao2.proxy.intercept;

public class DefaultInterceptor implements IInterceptor {

	@Override
	public Object before(InterceptContext context) throws Exception {

		System.out.println("Interceptor before");
		if (context.getArgsType() != null) {
			for (Class cls : context.getArgsType()) {
				System.out.println("argsType:" + cls.getName());
			}
		}
		if (context.getArgsValue() != null) {
			for (Object value : context.getArgsValue()) {
				System.out.println(value);
			}
		}
		return null;
	}

	@Override
	public Object after(InterceptContext context) throws Exception {

		System.out.println("Interceptor after");
		System.out.println("time cost:" + (context.getEndTime() - context.getStartTime()));
		return null;
	}

	@Override
	public Object excep(InterceptContext context) throws Exception {

		System.out.println("Interceptor excep");
		System.out.println("t:" + context.getT());
		return null;
	}
}
