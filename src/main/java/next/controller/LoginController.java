package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

@Controller
public class LoginController {

	@RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
	public	 ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return new ModelAndView(new JspView("/user/login.jsp"));
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		User user = DataBase.findUserById(userId);
		if (user == null) {
			request.setAttribute("loginFailed", true);
			return new ModelAndView(new JspView("/user/login.jsp"));
		}
		if (user.matchPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
			return new ModelAndView(new JspView("redirect:/"));
		}
		return new ModelAndView(new JspView("/user/login.jsp"));
	}
}
