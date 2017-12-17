package next.controller.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.Controller;
import next.dao.QuestionDao;

public class HomeController implements Controller {
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("questions", questionDao.findAll());
		return new ModelAndView(new JspView("home.jsp"));
	}

}
