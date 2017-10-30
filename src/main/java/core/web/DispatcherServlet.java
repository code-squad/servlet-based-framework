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
	RequestMapping requestmapping= new RequestMapping();
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		Controller controller = requestmapping.getController(req.getRequestURI());
		try {
			String view = controller.execute(req, resp);
			generateView(req, resp, controller, view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void generateView(HttpServletRequest req, HttpServletResponse resp, Controller controller, String view)
			throws IOException, ServletException {
		Map<String,Controller> model = new HashMap<>();
		model.put(view, controller);
		if (view.startsWith("redirect:")) {
		    resp.sendRedirect(view.substring("redirect:".length()));
		}
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
	

}