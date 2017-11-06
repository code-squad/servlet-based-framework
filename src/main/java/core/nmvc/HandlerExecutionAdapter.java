package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecutionAdapter implements HandlerAdapter{

	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerExecution;
	}

	@Override
	public ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
		HandlerExecution controller = (HandlerExecution)handler;
		
		return controller.handle(req, res);
	}

}
