package next.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DeleteAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnswerDao answerDao = new AnswerDao();
        Long answerId = Long.parseLong(request.getParameter("answerId"));
        log.debug("answerId : {}", answerId);

        Result result = answerDao.delete(answerId);

        writeJson(response, result);

        return null;
    }

    private void writeJson(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(getJson(result));
    }

    private String getJson(Result result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }
}
