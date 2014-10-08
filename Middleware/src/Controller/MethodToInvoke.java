package Controller;

import java.lang.reflect.Method;

public class MethodToInvoke{
	Method method;
	String topic;
	String data;
	String classes;

	MethodToInvoke(String classes,Method method,String data,String topic){
		this.data=data;
		this.method=method;
	this.classes=classes;
		this.topic=topic;
	}
	
	public String getTopic() {
		return topic;
	}
	public Method getMethod() {
		return method;
	}
	public String getdata() {
		return data;
	}
	public String getClasses() {
		return classes;
	}
}
