package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Object handleClassBean;
	private Method handleMethod;
	public HandlerExecution(Object handleClassBean, Method handleMethod) {
		this.handleClassBean = handleClassBean;
		this.handleMethod = handleMethod;
	}
	
	public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Object[] parameterArrays = {req, resp};
		return (ModelAndView)handleMethod.invoke(handleClassBean, parameterArrays);
	}
}
