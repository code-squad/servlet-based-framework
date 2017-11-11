package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

@Controller
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value="/users/create", method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
				req.getParameter("email"));
		DataBase.addUser(user);
		log.debug("user create success");
		return new ModelAndView(new JspView("redirect:/"));
	}
	
	@RequestMapping(value="/users/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		User user = DataBase.findUserById(req.getParameter("userId"));
		if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}
		User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
				req.getParameter("email"));
		log.debug("Update User : {}", updateUser);
		user.update(updateUser);
		return new ModelAndView(new JspView("redirect:/"));
	}
}