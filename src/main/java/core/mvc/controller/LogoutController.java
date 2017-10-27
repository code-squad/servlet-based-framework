package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import next.controller.UserSessionUtils;

public class LogoutController implements Controller {

	@Override
	public String run(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
		return "redirect:/home";
	}

}
