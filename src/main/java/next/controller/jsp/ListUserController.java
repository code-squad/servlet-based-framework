package next.controller.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import next.dao.UserDao;

@Controller
public class ListUserController {
	private UserDao userDao = UserDao.getInstance();

	@RequestMapping("/users")
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}
		request.setAttribute("users", userDao.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}
}
