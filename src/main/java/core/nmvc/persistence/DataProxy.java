package core.nmvc.persistence;

import java.lang.reflect.Method;

public class DataProxy implements java.lang.reflect.InvocationHandler{
		
	private DataProxy(Object obj) {
	}
	
	public static Object newInstance(Object obj) {
		return java.lang.reflect.Proxy.newProxyInstance(obj.getClass()
				.getClassLoader(), obj.getClass().getInterfaces(), 
				new DataProxy(obj));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}

}
