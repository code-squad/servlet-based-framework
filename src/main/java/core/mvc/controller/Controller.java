package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import core.mvc.ModelAndView;

public interface Controller {
	public ModelAndView run(HttpServletRequest req);
}
