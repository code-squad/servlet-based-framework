package core.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;
import next.controller.Controller;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private RequestMapping requestMapping;

	@Override
	public void init() throws ServletException {
		requestMapping = RequestMapping.createRequestMapping();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Controller controller = requestMapping.findController(req.getRequestURI());
		try {
			ModelAndView mav = controller.execute(req, resp);
			mav.render(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
