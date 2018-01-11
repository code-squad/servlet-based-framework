package next.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.View;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	private static final long serialVersionUID = 1L;
	private RequestMapping requestMapping = new RequestMapping();

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("requestUrl : {}", req.getRequestURI().toString());
		String uri = req.getRequestURI();
		Controller controller = requestMapping.mappingController(uri);
		try {
			View view = controller.execute(req, res);
			view.render(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}