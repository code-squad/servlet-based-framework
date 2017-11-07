package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class ShowQuestionController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		QuestionDao QuestionDao = new QuestionDao();
		List<Question> savedQuestion = QuestionDao.findAll();
		ModelAndView mav = new ModelAndView(new JsonView());
		mav.addObject("question", savedQuestion);
		return mav;
	}
}
