package next.controller;

import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowQuestionController implements CommonController {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // questions
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);
        request.setAttribute("question", question);
        // answers
        AnswerDao answerDao = new AnswerDao();
        request.setAttribute("answers", answerDao.findByQuestionId(questionId));

        return "/qna/show.jsp";
    }
}
