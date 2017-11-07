package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Class<?> controllerClass;
	private Method method;

	public HandlerExecution(Class<?> controllerClass, Method method) {
		this.controllerClass = controllerClass;
		this.method = method;
	}

	public ModelAndView handle(HttpServletRequest req) throws Exception {
		return (ModelAndView) this.method.invoke(controllerClass.newInstance(), req);
	}
}
