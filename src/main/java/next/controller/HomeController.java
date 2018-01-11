package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("users", DataBase.findAll());
		return new ModelAndView(new JspView("/index.jsp"));
	}
}
