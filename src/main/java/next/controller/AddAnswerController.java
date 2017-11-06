package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

public class AddAnswerController implements Controller{
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		log.debug("add Answer Controller");
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), questionId);
		AnswerDao answerDao = AnswerDao.getInstance();
		QuestionDao questionDao = QuestionDao.getInstance();
		long answerId = answerDao.insert(answer);
		Answer savedAnswer = answerDao.findByAnswerId(answerId);
		resp.setContentType("application/json;charset=UTF-8");
		responseJson(resp.getWriter(), savedAnswer);
		questionDao.editCountOfAnswer(questionId, 1);
		return null;
	}
	public void responseJson(PrintWriter pw, Answer answer) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		pw.print(om.writeValueAsString(answer));
	}

}
