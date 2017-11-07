package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class HomeController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mav = new ModelAndView(new JspView("home"));
		log.debug("home controller");
		QuestionDao questionDao = QuestionDao.getInstance();
		mav.addObject("questions", questionDao.findAll());
		return mav;
	}

}
