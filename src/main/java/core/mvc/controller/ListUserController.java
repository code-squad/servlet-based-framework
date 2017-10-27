package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import next.controller.UserSessionUtils;

public class ListUserController implements Controller{

	@Override
	public String run(HttpServletRequest req) {
		if(!UserSessionUtils.isLogined(req.getSession())) {
			return "redirect:/users/loginForm";
		}
		
		req.setAttribute("users", DataBase.findAll());
		return "/user/list.jsp";
	}

}
