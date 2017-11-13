package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.mvc.ModelAndView;

public interface HandlerAdapter {
	boolean supports(Object handler);
	ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
