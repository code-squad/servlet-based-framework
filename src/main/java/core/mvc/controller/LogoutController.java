package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

public class LogoutController implements Controller {

	@Override
	public ModelAndView run(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
		return new ModelAndView(new JspView("redirect:/home"));
	}

}
