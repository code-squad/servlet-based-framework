package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.JspView;
import core.mvc.View;

public class HomeController implements Controller {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("users", DataBase.findAll());
		return new JspView("/index.jsp");
	}
}
