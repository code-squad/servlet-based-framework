package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.UserDao;

public class ListUserController implements Controller {
	private UserDao userDao = UserDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return "redirect:/users/loginForm";
		}

		request.setAttribute("users", userDao.findAll());

		return "/user/list.jsp";
	}

}
