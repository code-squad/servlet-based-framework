package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;

public class UpdateFormController implements LegacyControllerInterface{

	@Override
	public ModelAndView run(HttpServletRequest req) {
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);
		
		ModelAndView mav = new ModelAndView(new JspView("/user/updateForm.jsp"));

		if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("남의 정보를 수정하면 안 되죠.");
		}
		mav.addObject("user", user);
		
		return mav;
	}

}
