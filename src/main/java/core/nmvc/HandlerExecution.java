package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Method method;
	private Object obj;

	public HandlerExecution(Object obj, Method method) {
		this.method = method;
		this.obj = obj;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
		try {
			return (ModelAndView) method.invoke(obj, request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}