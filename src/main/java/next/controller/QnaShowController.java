package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

public class QnaShowController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(QnaShowController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		
		QuestionDao questionDao = QuestionDao.getInstance();
		AnswerDao answerDao = AnswerDao.getInstance();
		long questionId = Integer.parseInt(req.getParameter("questionId"));
		log.debug("questionId : " + questionId);
		Question question = questionDao.findByQuestionId(questionId);
		ModelAndView mav = new ModelAndView(new JspView("/qna/show.jsp"));
		mav.addObject("question", question);
		mav.addObject("answers", answerDao.findAllByQuestionId(questionId));
		return mav;
	}

}
