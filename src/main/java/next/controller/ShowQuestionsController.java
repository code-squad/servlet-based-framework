package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.CommonController;
import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShowQuestionsController implements CommonController {
    private static final Logger log = LoggerFactory.getLogger(ShowQuestionsController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        QuestionDao questionDao = new QuestionDao();
        List<Question> questions = questionDao.findAll();

        for (Question q : questions) {
            log.debug("question: {}", q.toString());
        }

        // object -> json (Jackson library)
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(mapper.writeValueAsString(questions));
        return null;
    }
}
