package next.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final long serialVersionUID = 1L;
	private static final RequestMapping requestMapper = new RequestMapping();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("request method: " + req.getMethod() + " request uri : " + req.getRequestURI());
		try {
			Controller controller = requestMapper
					.getMatchController(new RequestMethod(req.getRequestURI(), req.getMethod()));
			controller = controller == null ? new ForwardController(req.getRequestURI()) : controller;
			ModelAndView modelAndView = controller.execute(req, resp);
			modelAndView.getView().render(modelAndView.getModel(), req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
