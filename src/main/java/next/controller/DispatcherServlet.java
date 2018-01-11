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
import core.nmvc.AnnotationHandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	private static final long serialVersionUID = 1L;
	private AnnotationHandlerMapping ahm;
	// private RequestMapping requestMapping = new RequestMapping();

	@Override
	public void init() {
		ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();
	}

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
		try {
			log.debug("requestUrl : {}", req.getRequestURI().toString());
			ModelAndView mv = ahm.getHandler(req).handle(req, res);
			log.debug("ModelAndView : {}", mv);
			mv.addObject(createModel(req));
			log.debug("addObject Run");
			mv.getView().render(req, res);
			log.debug("render Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}