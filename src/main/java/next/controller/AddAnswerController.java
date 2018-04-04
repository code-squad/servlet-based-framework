package next.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import core.mvc.RestController;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddAnswerController implements RestController<Answer> {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public void executeAjax(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // create Answer object using data from Http request

        HttpSession session = req.getSession();
//        if(session == null){// 로그인 안한 상태
//            return "/users/login.jsp";
//        }

        User user = (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
        String questionId = req.getParameter("questionId");
        log.debug("questionId:{}", questionId);

        Answer answer = new Answer(user.getUserId(), req.getParameter("contents"), Long.parseLong(req.getParameter("questionId")));

        log.debug("answer : {}", answer.toString());
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        // object -> json (Jackson library)
        writeJson(res, getJson(savedAnswer));
    }
}
