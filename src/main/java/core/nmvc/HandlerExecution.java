package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	Class<?> clazz;
	Method method;

	public HandlerExecution(Class<?> clazz, Method method) {
		this.clazz = clazz;
		this.method = method;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (ModelAndView) method.invoke(clazz.newInstance(), request, response);
	}
}
