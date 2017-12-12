package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;

public class HomeController implements Controller {
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("questions", questionDao.findAll());
		return "home.jsp";
	}

}
