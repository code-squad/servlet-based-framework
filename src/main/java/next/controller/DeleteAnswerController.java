package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.View;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		answerDao.deleteByAnswerId(Long.parseLong(request.getParameter("answerId")));
		return ApiJsonMapper.mapping(response, Result.ok());
	}
}
