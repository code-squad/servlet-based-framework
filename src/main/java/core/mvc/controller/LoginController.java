package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;

@Controller
public class LoginController implements LegacyControllerInterface {

	@Override
	@RequestMapping("/users/loginForm")
	public ModelAndView run(HttpServletRequest req) {
		return new ModelAndView(new JspView("/user/login.jsp"));
	}

}
