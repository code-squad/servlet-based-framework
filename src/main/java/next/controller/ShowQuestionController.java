package next.controller;

import core.mvc.Controller;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowQuestionController  implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        request.setAttribute("question", questionDao.findById(questionId));
        return "/qna/show.jsp";
    }
}
