package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class UpdateUserController implements Controller {

	@Override
	public ModelAndView run(HttpServletRequest req) {
		User user = DataBase.findUserById(req.getParameter("userId"));
		if(!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("남의 정보를 도둑질하고 있습니까?");
		}
		
		User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
				req.getParameter("email"));
		
		user.update(updateUser);
		DataBase.addUser(user);
		return new ModelAndView(new JspView("redirect:/home"));
				
	}
}
