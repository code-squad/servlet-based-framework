package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import core.mvc.controller.LegacyControllerInterface;

public class LegacyControllerAdapter implements HandlerAdapter{

	@Override
	public boolean supports(Object handler) {
		return handler instanceof LegacyControllerInterface;
	}

	@Override
	public ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
		LegacyControllerInterface controller = (LegacyControllerInterface)handler;
		
		return controller.run(req);
	}

}
