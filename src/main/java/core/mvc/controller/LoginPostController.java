package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class LoginPostController implements Controller {

	@Override
	public ModelAndView run(HttpServletRequest req) {
		User user = DataBase.findUserById(req.getParameter("userId"));
		
		if (user == null) {
			return new ModelAndView(new JspView("redirect:/home"));
		}
		
		if (user.matchPassword(req.getParameter("password"))) {
			HttpSession session = req.getSession();
			session.setAttribute("loginuser", user);
			System.err.println("someone just logined by using HttpSession.");
			return new ModelAndView(new JspView("redirect:/home"));
		}
		
		return new ModelAndView(new JspView("redirect:/home"));
	}

}
