package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import next.model.User;

public class ProfileController implements Controller {

	@Override
	public String run(HttpServletRequest req) {
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);

		if (user == null) {
			throw new NullPointerException("No user matches with your request.");
		}

		req.setAttribute("user", user);
		return "/user/profile.jsp";
	}
}
