package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

public class QnaShowController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(QnaShowController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		QuestionDao questionDao = QuestionDao.getInstance();
		AnswerDao answerDao = AnswerDao.getInstance();
		long questionId = Integer.parseInt(req.getParameter("questionId"));
		Question question = questionDao.findByQuestionId(questionId);
		log.debug("qeustion : " + question);
		req.setAttribute("question", question);
		req.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
		return "/qna/show.jsp";
	}

}
