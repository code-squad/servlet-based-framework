package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Result;

public class DeleteAnswerController implements Controller{
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		AnswerDao answerDao = AnswerDao.getInstance();
		try {
			int answerId = Integer.parseInt(req.getParameter("answerId"));
			long questionId = answerDao.findByAnswerId(answerId).getQuestionId();
			answerDao.delete(answerId);
			log.debug("remove success");
			PrintWriter pw = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			pw.print(mapper.writeValueAsString(Result.ok()));
			QuestionDao questionDao = QuestionDao.getInstance();
			questionDao.editCountOfAnswer(questionId, -1);
		} catch(Exception e) {
			PrintWriter pw = resp.getWriter();
			pw.print(Result.fail("fail"));
		}
		return null;
	}

}
