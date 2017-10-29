package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;

public class HomeController implements Controller{

	@Override
	public ModelAndView run(HttpServletRequest req) {
		return new ModelAndView(new JspView("index.jsp"));
	}
}
