package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class HomeController implements LegacyController {
	private static final long serialVersionUID = 1L;
	private QuestionDao questionDao = QuestionDao.getInstance();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("question", questionDao.findAll());
		return new ModelAndView(new JspView("index.jsp"));
	}
}
