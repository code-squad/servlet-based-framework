package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import next.controller.UserSessionUtils;
import next.model.User;

public class UpdateFormController implements Controller{

	@Override
	public String run(HttpServletRequest req) {
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);

		if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("남의 정보를 수정하면 안 되죠.");
		}
		req.setAttribute("user", user);
		return "/user/updateForm.jsp";
	}

}
