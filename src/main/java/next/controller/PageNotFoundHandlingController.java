package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JspView;
import core.mvc.ModelAndView;

public class PageNotFoundHandlingController implements LegacyController{
	private static final Logger logger = LoggerFactory.getLogger(PageNotFoundHandlingController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("404 controller");
		return new ModelAndView(new JspView("/errorPage.jsp"));
	}
}
