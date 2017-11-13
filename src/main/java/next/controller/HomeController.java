package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

@Controller
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView(new JspView("home.jsp"));
		log.debug("home controller");
		QuestionDao questionDao = QuestionDao.getInstance();
		mav.addObject("questions", questionDao.findAll());
		return mav;
	}

}
