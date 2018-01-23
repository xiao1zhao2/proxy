package com.xiao1zhao2.proxy.factory;

import com.xiao1zhao2.proxy.annotation.IIntercept;
import com.xiao1zhao2.proxy.intercept.IInterceptor;
import javassist.*;

import java.lang.reflect.Method;

public class ProxyFactory {

	private ProxyFactory() {
	}

	public static <T> T getService(Class<T> targetClass) throws Exception {

		String targetInterfaceName = targetClass.getName();
		String targetClassName = targetInterfaceName.replaceFirst("I", "");

		String proxyClassName = targetClassName + "$" + System.currentTimeMillis();

		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("com.xiao1zhao2.proxy.intercept");
		pool.importPackage("java.util");

		CtClass proxyCtCls = pool.makeClass(proxyClassName);

		proxyCtCls.addInterface(pool.getCtClass(targetInterfaceName));

		String proxyFieldString = "private " + targetInterfaceName + " realService = new " + targetClassName + "();";
		CtField proxyField = CtField.make(proxyFieldString, proxyCtCls);
		proxyCtCls.addField(proxyField);
//		System.out.println("proxyFieldString:" + proxyFieldString);

		IIntercept intercept = targetClass.getAnnotation(IIntercept.class);
		Class<? extends IInterceptor> cls = intercept.intercept();
		String interceptFieldString = "private " + cls.getName() + " intercept = new " + cls.getName() + "();";
		CtField interceptField = CtField.make(interceptFieldString, proxyCtCls);
		proxyCtCls.addField(interceptField);
//		System.out.println(("interceptFieldString:" + interceptFieldString));

		CtConstructor cons = new CtConstructor(new CtClass[]{}, proxyCtCls);
		cons.setBody("{}");
		proxyCtCls.addConstructor(cons);

		Method[] methods = targetClass.getDeclaredMethods();

		for (Method method : methods) {
			String methodStr = buildMethodString(method);
			CtMethod ctMethod = CtMethod.make(methodStr, proxyCtCls);
			proxyCtCls.addMethod(ctMethod);
		}

		Class<T> proxyCls = proxyCtCls.toClass();
		proxyCtCls.detach();
		return proxyCls.newInstance();
	}

	private static String buildMethodString(Method method) {

		StringBuilder sb = new StringBuilder();

		sb.append("public " + method.getReturnType().getName() + " " + method.getName() + "(" + getMethodArgsFullString(method) + ") throws Exception{\n");
		sb.append("InterceptContext context = new InterceptContext();\n");
		sb.append("context.setStartTime(System.currentTimeMillis());\n");

		sb.append("List argsTypeList = new ArrayList();\n");
		if (method.getParameterTypes() != null && method.getParameterTypes().length > 0) {
			for (Class cls : method.getParameterTypes()) {
				sb.append("argsTypeList.add(" + cls.getSimpleName() + ".class);\n");
			}
		}
		sb.append("context.setArgsType(argsTypeList);\n");
		sb.append("intercept.before(context);\n");
		sb.append(method.getReturnType().getName() + " response=null;\n");

		sb.append("try{\n");
		sb.append("response=realService." + method.getName() + "(" + getMethodArgsValueString(method) + ");\n");
		sb.append("}\n");
		sb.append("catch(Throwable t){\n");
		sb.append("context.setT(t);\n");
		sb.append("intercept.excep(context);\n");
		sb.append("}\n");
		sb.append("context.setEndTime(System.currentTimeMillis());\n");
		sb.append("intercept.after(context);\n");
		sb.append("return response;\n");
		sb.append("}\n");
//		System.out.println(sb.toString());
		return sb.toString();
	}

	private static String getMethodArgsFullString(Method method) {
		StringBuilder sb = new StringBuilder();
		Class[] types = method.getParameterTypes();
		if (types != null && types.length > 0) {
			for (int i = 0; i < types.length; i++) {
				sb.append(types[i].getName() + " param" + i + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private static String getMethodArgsValueString(Method method) {
		StringBuilder sb = new StringBuilder();
		Class[] types = method.getParameterTypes();
		if (types != null && types.length > 0) {
			for (int i = 0; i < types.length; i++) {
				sb.append("param" + i + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

}
