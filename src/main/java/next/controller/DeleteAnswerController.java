package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		answerDao.deleteByAnswerId(Long.parseLong(request.getParameter("answerId")));
		ModelAndView mv = new ModelAndView(new JsonView(Result.ok()));
		mv.addObject(request);
		return mv;
	}
}
