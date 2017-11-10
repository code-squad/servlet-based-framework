package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class ProfileController implements LegacyController {
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		log.debug("get user profile");
		ModelAndView mav = new ModelAndView(new JspView("/user/profile.jsp"));
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}
		mav.addObject("user", user);
		return new ModelAndView(new JspView("/user/profile.jsp"));
	}
}
