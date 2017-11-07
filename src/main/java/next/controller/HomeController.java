package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class HomeController implements Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		request.setAttribute("question", questionDao.findAll());
		return new ModelAndView(new JspView("index.jsp"));
	}
}
