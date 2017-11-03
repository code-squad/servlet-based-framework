package core.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

public class ListUserController implements Controller{

	@Override
	public ModelAndView run(HttpServletRequest req) {
		if(!UserSessionUtils.isLogined(req.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}
		ModelAndView mav = new ModelAndView(new JspView("/user/list.jsp"));
		mav.addObject("users", DataBase.findAll());
		return mav;
	}
}
