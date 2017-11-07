package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.jdbc.JdbcManager;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;


public class CreateUserController implements LegacyControllerInterface {


	@Override
	public ModelAndView run(HttpServletRequest req) {
		JdbcManager jdbm = JdbcManager.getInstance();
		
		User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
				req.getParameter("email"));
		if (User.isValid(user)) {
			
			DataBase.addUser(user);
			
			return new ModelAndView(new JspView("redirect:/home"));
		}
		return new ModelAndView(new JspView("redirect:/users/form"));
	}
}
