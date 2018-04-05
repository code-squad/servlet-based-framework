package next.controller;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowQuestionController implements Controller {

    @Override
    public View execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // questions
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);
        req.setAttribute("question", question);
        // answers
        AnswerDao answerDao = new AnswerDao();
        req.setAttribute("answers", answerDao.findByQuestionId(questionId));
        return new JspView("/qna/show.jsp");
    }
}
