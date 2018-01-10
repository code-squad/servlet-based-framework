package next.controller;

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JsonView;
import core.mvc.ModelAndView;
=======
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

>>>>>>> feat(ajax): answer 추가 삭제(ajax)
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {

	@Override
<<<<<<< HEAD
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		answerDao.deleteByAnswerId(Long.parseLong(request.getParameter("answerId")));
		ModelAndView mv = new ModelAndView(new JsonView(Result.ok()));
		mv.addObject(request);
		return mv;
=======
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		answerDao.deleteByAnswerId(Long.parseLong(request.getParameter("answerId")));
		return ApiJsonMapper.mapping(response, Result.ok());
>>>>>>> feat(ajax): answer 추가 삭제(ajax)
	}
}
