package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class ProfileController implements Controller {

	@Override
	public ModelAndView run(HttpServletRequest req) {
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("No user matches with your request.");
		}
		ModelAndView mav = new ModelAndView(new JspView("/user/profile.jsp"));
		mav.addObject("user", user);
		
		return mav;
	}
}
