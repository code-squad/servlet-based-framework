package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		answerDao.deleteByAnswerId(Long.parseLong(request.getParameter("answerId")));
		return ApiJsonMapper.mapping(response, Result.ok());
	}
}
