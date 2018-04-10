package core.mvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import next.controller.CreateQuestionController;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    @RequestMapping(value = "/qna/create", method = RequestMethod.POST)
    public ModelAndView createQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Question question = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
        log.debug("Question : {}", question);

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);

        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/qna")
    public ModelAndView showQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // questions
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);
        req.setAttribute("question", question);
        // answers
        AnswerDao answerDao = new AnswerDao();
        return new ModelAndView(new JspView("/qna/show.jsp")).addObject("answers", answerDao.findByQuestionId(questionId));
    }

    @RequestMapping(value = "/qna/form")
    public ModelAndView questionForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
         return new ModelAndView(new JspView("/qna/form.jsp"));
    }
}
