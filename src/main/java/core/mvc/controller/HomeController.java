package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller{

	@Override
	public String run(HttpServletRequest req) {
		return "index.jsp";
	}
}
