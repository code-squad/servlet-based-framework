package next.controller;

import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements CommonController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        Question question = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
        log.debug("Question : {}", question);

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);

        return "redirect:/";
    }
}
