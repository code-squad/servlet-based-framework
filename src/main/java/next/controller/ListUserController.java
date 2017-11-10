package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

public class ListUserController implements LegacyController {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView(new JspView("/user/list.jsp"));
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}
		mav.addObject("users", DataBase.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}
}
