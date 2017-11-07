package core.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import next.controller.Controller;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private RequestMapping requestmapping;

	@Override
	public void init() {
		requestmapping = new RequestMapping();
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Controller controller = requestmapping.getController(req.getRequestURI());
		try {
			ModelAndView mav = controller.execute(req, resp);
			mav.getView().render(mav.getModel(), req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}