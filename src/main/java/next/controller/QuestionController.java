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
import next.model.Question;

@Controller
public class QuestionController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@RequestMapping(value="/qna/form", method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Question expected = new Question(req.getParameter("writer"), req.getParameter("title"),
				req.getParameter("contents"));
		QuestionDao questionDao = QuestionDao.getInstance();
		questionDao.insert(expected);
		log.debug("insert succeess");
		return new ModelAndView(new JspView("redirect:/"));
	}
	
	@RequestMapping(value="/qna/form", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return new ModelAndView(new JspView("/qna/form.jsp"));
	}
	

}
