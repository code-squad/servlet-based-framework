package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.jdbc.DataAccessException;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	AnswerDao answrDao = AnswerDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String answerId = request.getParameter("answerId");

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		answrDao.delete(Long.parseLong(answerId));
		out.print(mapper.writeValueAsString(Result.ok()));
		return null;
	}
}
