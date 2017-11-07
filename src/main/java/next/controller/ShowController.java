package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController implements Controller {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		req.setAttribute("question", questionDao.findById(questionId));
		req.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
		return new ModelAndView(new JspView("/qna/show.jsp"));
	}
}
