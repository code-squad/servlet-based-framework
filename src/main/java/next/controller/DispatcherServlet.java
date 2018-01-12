package next.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;
import core.mvc.View;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	private static final long serialVersionUID = 1L;
	private RequestMapping requestMapping = new RequestMapping();
	
	private Map<String, Object> createModel(HttpServletRequest request) {
		Enumeration<String> names = request.getAttributeNames();
		Map<String, Object> model = new HashMap<>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			model.put(name, request.getAttribute(name));
		}
		return model;
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("requestUrl : {}", req.getRequestURI().toString());
		String uri = req.getRequestURI();
		Controller controller = requestMapping.mappingController(uri);
		try {
			ModelAndView mv = controller.execute(req, res);
			mv.addObject(createModel(req));
			mv.getView().render(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}