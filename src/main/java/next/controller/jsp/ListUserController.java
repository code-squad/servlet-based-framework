package next.controller.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.Controller;
import next.dao.UserDao;

public class ListUserController implements Controller {
	private UserDao userDao = UserDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		request.setAttribute("users", userDao.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}

}
