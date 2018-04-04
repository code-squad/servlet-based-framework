package next.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.mvc.CommonController;
import core.mvc.Controller;
import core.mvc.RestController;
import next.dao.AnswerDao;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteAnswerController implements RestController<Result> {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);

    @Override
    public void executeAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnswerDao answerDao = new AnswerDao();
        String para = request.getParameter("answerId");
        log.debug("answerId : {}", para);

        Long answerId = Long.parseLong(para);

        Result result = answerDao.delete(answerId);

        writeJson(response, getJson(result));
    }

}
