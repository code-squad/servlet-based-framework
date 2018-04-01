package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // create Answer object using data from Http request
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), req.getIntHeader("questionId"));

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        // object -> json (Jackson library)
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(mapper.writeValueAsString(savedAnswer));
        return null;
    }
}
