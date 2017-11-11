package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.DataAccessException;
import next.dao.QuestionDao;
import next.model.Answer;

public class AddAnswerController implements LegacyController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView(new JsonView());
		log.debug("add Answer Controller");
		try {
			long questionId = Long.parseLong(req.getParameter("questionId"));
			Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), questionId);
			AnswerDao answerDao = AnswerDao.getInstance();
			QuestionDao questionDao = QuestionDao.getInstance();
			mav.addObject("answer", answerDao.insert(answer));
			questionDao.editCountOfAnswer(questionId, 1);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("댓글을 추가할 수 없습니다.");
		}
	}
}