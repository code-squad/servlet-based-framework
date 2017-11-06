package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.mvc.JspView;
import core.mvc.ModelAndView;

public class UserJoinFormController implements LegacyControllerInterface{

	@Override
	public ModelAndView run(HttpServletRequest req) {
		return new ModelAndView(new JspView("/user/form.jsp"));
	}

}
