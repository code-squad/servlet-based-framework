package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Class<?> handleClass;
	private Method handleMethod;
	public HandlerExecution(Class<?> handleClass, Method handleMethod) {
		this.handleClass = handleClass;
		this.handleMethod = handleMethod;
	}
	
	public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Object[] parameterArrays = {req, resp};
		return (ModelAndView)handleMethod.invoke(handleClass.newInstance(), parameterArrays);
	}
}
