package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public interface HandlerAdapter {
	
	public boolean supports(Object handler);
	public ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception;

}
