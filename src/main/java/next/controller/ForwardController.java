package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;

public class ForwardController implements LegacyController {

	private String url;

	public ForwardController(String url) {
		this.url = url;
	}

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return new ModelAndView(new JspView(url + ".jsp"));
	}

}
