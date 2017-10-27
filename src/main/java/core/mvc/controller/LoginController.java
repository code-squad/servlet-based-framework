package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

public class LoginController implements Controller{

	@Override
	public String run(HttpServletRequest req) {
		return "/user/login.jsp";
	}

}
