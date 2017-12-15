package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	private AnswerDao answerDao = AnswerDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long answerId = Long.parseLong(request.getParameter("answerId"));
		answerDao.delete(answerId);
		ModelAndView mav = new ModelAndView(new JsonView());
		mav.setAttribute("result", Result.ok());
		return mav;
	}
}
