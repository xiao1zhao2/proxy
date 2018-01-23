package com.xiao1zhao2.proxy.annotation;


import com.xiao1zhao2.proxy.intercept.IInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IIntercept {

	Class<? extends IInterceptor> intercept();
}
