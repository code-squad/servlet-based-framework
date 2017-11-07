package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class CreateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		log.debug("user create start");
		User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
				req.getParameter("email"));
		DataBase.addUser(user);
		return new ModelAndView(new JspView("redirect:/"));
	}
}
