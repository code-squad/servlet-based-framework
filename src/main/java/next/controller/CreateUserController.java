package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.View;
import next.model.User;

public class CreateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
				request.getParameter("name"), request.getParameter("email"));
		log.debug("User : {}", user);
		DataBase.addUser(user);
		
		return new JspView("redirect:/users");
	}
}
