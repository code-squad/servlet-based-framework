package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class CreateUserController implements Controller{

	@Override
	public ModelAndView run(HttpServletRequest req) {
		if (isCorrectUserInfo(req)) {
			User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
			DataBase.addUser(user);
			return new ModelAndView(new JspView("redirect:/home"));
		}
		return new ModelAndView(new JspView("redirect:/users/form"));
	}
	
	private boolean isCorrectUserInfo(HttpServletRequest req) {
		return !empty(req.getParameter("userId")) && !empty(req.getParameter("password")) && !empty(req.getParameter("name")) && !empty(req.getParameter("email"));
	}
	
	private boolean empty(String str) {
		return str == null || str.trim().isEmpty();
	}

}
