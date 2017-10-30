package core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.JspView;
import next.controller.Controller;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		RequestMapping requestmapping = new RequestMapping();
		requestmapping.init();
		Controller controller = requestmapping.getController(req.getRequestURI());
		try {
			String view = controller.execute(req, resp);
			JspView jspView = new JspView(view);
			Map<String,Controller> model = new HashMap<>();
			model.put(view, controller);
			jspView.render(model, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}