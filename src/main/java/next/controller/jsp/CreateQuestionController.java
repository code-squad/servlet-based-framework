package next.controller.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.Controller;
import next.dao.QuestionDao;
import next.model.Question;

public class CreateQuestionController implements Controller {
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Question question = new Question(
				request.getParameter("writer"), 
				request.getParameter("title"),
				request.getParameter("contents"));
		question = questionDao.insert(question);
		
		return new ModelAndView(new JspView("redirect:/qna/show?questionId=" + question.getQuestionId()));
	}
}
