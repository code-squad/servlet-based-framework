package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;

@Controller
public class FilePathController {

	@RequestMapping(value = "/users/form", method = RequestMethod.GET)
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new JspView("/user/form.jsp"));
	}

}
