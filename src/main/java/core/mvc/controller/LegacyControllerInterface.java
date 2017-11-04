package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import core.mvc.ModelAndView;

public interface LegacyControllerInterface {
	public ModelAndView run(HttpServletRequest req);
}
