package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ListUserController implements LegacyController {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/users")
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}
		
		request.setAttribute("users", DataBase.findAll());
		return new ModelAndView(new JspView("/user/list.jsp"));
	}
	
	
}
