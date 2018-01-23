package com.xiao1zhao2.proxy.intercept;

import java.util.List;

public class InterceptContext {

	private long startTime;
	private long endTime;
	private List<Class> argsType;
	private List<Object> argsValue;
	private Object returnValue;
	private Throwable t;

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public List<Class> getArgsType() {
		return argsType;
	}

	public void setArgsType(List<Class> argsType) {
		this.argsType = argsType;
	}

	public List<Object> getArgsValue() {
		return argsValue;
	}

	public void setArgsValue(List<Object> argsValue) {
		this.argsValue = argsValue;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public Throwable getT() {
		return t;
	}

	public void setT(Throwable t) {
		this.t = t;
	}
}
