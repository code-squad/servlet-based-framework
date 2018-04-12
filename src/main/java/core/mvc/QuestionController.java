package core.mvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.service.AnswerService;
import next.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @RequestMapping(value = "/qna/form")
    public ModelAndView questionForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
        return new ModelAndView(new JspView("/qna/form.jsp"));
    }

    @RequestMapping(value = "/qna", method = RequestMethod.POST)
    public ModelAndView createQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Question question = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
        log.debug("Question : {}", question);

        QuestionDao questionDao = new QuestionDao();
        QuestionService questionService = new QuestionService(questionDao);
        questionService.insert(question);

        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/qna")
    public ModelAndView showQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // questions
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        QuestionDao questionDao = new QuestionDao();
        QuestionService questionService = new QuestionService(questionDao);
        Question question = questionService.findById(questionId);

        req.setAttribute("question", question);
        // answers
        AnswerDao answerDao = new AnswerDao();
        AnswerService answerService = new AnswerService(answerDao);

        return new ModelAndView(new JspView("/qna/show.jsp")).addObject("answers", answerService.findByQuestionId(questionId));
    }
}
