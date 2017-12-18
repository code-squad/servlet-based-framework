package next.controller.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.Controller;

public class ForwardController implements Controller {
	private String path;

	public ForwardController(String path) {
		this.path = path + ".jsp";
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new JspView(path));
	}

}
