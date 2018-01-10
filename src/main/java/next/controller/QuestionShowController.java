package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
import core.mvc.JspView;
import core.mvc.ModelAndView;
=======
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class QuestionShowController implements Controller {
	@Override
<<<<<<< HEAD
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
=======
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
		Long questionId = Long.parseLong(request.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        request.setAttribute("question", questionDao.findById(questionId));
        request.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
<<<<<<< HEAD
        return new ModelAndView(new JspView("/qna/show.jsp"));
=======
        return "/qna/show.jsp";
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
	}
}
