package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

public class ListUserController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}
		request.setAttribute("users", DataBase.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}
}
