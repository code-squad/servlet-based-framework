package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Result;

public class DeleteAnswerController implements Controller{
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView(new JsonView());
		AnswerDao answerDao = AnswerDao.getInstance();
		try {
			log.debug("deleteAnswerController");
			int answerId = Integer.parseInt(req.getParameter("answerId"));
			long questionId = answerDao.findByAnswerId(answerId).getQuestionId();
			answerDao.delete(answerId);
			QuestionDao questionDao = QuestionDao.getInstance();
			questionDao.editCountOfAnswer(questionId, -1);
			mav.addObject("status", Result.ok());
		} catch(Exception e) {
			PrintWriter pw = resp.getWriter();
			pw.print(Result.fail("fail"));
		}
		return mav;
	}

}
