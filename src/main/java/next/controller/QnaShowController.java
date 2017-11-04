package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;

public class QnaShowController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		QuestionDao questionDao = QuestionDao.getInstance();
		Question question = questionDao.findByQuestionId(Integer.parseInt(req.getParameter("questionId")));
		req.setAttribute("question", question);
		return "qna/show";
	}

}
