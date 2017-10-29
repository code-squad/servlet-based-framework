package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import core.mvc.View;

public interface Controller {
	public ModelAndView run(HttpServletRequest req);
}
