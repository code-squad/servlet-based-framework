package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class AddQuestionController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddQuestionController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		Question expected = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
		QuestionDao questionDao = QuestionDao.getInstance();
		questionDao.insert(expected);
		log.debug("insert succeess");
		return new ModelAndView(new JspView("redirect:/"));
	}

}
