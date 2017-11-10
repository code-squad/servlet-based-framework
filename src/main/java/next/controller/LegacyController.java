package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.mvc.ModelAndView;

public interface LegacyController {
	ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
