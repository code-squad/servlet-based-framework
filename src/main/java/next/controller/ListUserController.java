package next.controller;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		request.setAttribute("users", DataBase.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}
}
