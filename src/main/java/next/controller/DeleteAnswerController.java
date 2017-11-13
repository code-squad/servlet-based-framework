package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;


public class DeleteAnswerController implements LegacyController {

	private AnswerDao answerDao = AnswerDao.getInstance();
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long answerId = Long.parseLong(req.getParameter("answerId"));
		answerDao.delete(answerId);
		return new ModelAndView(new JsonView());
	}
}