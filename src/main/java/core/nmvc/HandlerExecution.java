package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import next.controller.LegacyController;

public class HandlerExecution implements LegacyController {
	private Class<?> handleClass;
	private Method handleMethod;
	public HandlerExecution(Class<?> handleClass, Method handleMethod) {
		this.handleClass = handleClass;
		this.handleMethod = handleMethod;
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Object[] parameterArrays = {req, resp};
		return (ModelAndView)handleMethod.invoke(handleClass.newInstance(), parameterArrays);
	}
}
