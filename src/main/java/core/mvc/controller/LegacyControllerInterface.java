package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import core.mvc.ModelAndView;

@FunctionalInterface
public interface LegacyControllerInterface {
	public ModelAndView run(HttpServletRequest req);
}
