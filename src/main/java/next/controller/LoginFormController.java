package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFormController implements Controller{
	private static final Logger log = LoggerFactory.getLogger(LoginFormController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		log.debug("loginForm");
		return "/user/login.jsp";
	}

}
