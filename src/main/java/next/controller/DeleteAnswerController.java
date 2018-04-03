package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnswerDao answerDao = new AnswerDao();
        Long answerId = Long.parseLong(request.getParameter("answerId"));
        log.debug("answerId : {}", answerId);

        answerDao.delete(answerId);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if(answerDao.findById(answerId) == null){
            out.print(mapper.writeValueAsString(Result.ok()));
        }
        else out.print(mapper.writeValueAsString(Result.fail("error message")));

        return null;
    }
}
