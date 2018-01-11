package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

@Controller
public class QuestionShowController {
	@RequestMapping(value = "/qna/show", method = RequestMethod.GET)
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		request.setAttribute("question", questionDao.findById(questionId));
		request.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
		return new ModelAndView(new JspView("/qna/show.jsp"));
	}
}
