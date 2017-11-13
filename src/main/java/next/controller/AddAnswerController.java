package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;
import core.annotation.*;

public class AddAnswerController implements LegacyController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	private AnswerDao answerDao = AnswerDao.getInstance(); 
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
				Long.parseLong(req.getParameter("questionId")));
		log.debug("answer : {}", answer);

		
		Answer savedAnswer = answerDao.insert(answer);
		ModelAndView mav = new ModelAndView(new JsonView());
		mav.addObject("answer", savedAnswer);
		return mav;
	}
}