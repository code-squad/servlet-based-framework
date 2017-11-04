package core.web.requestservlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.controller.LegacyControllerInterface;
import core.mvc.requestmapping.RequestLine;
import core.nmvc.AnnotationHandlerMapping;
import core.mvc.requestmapping.LegacyRequestMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LegacyRequestMapping rm;
	private AnnotationHandlerMapping ahm;

	@Override
	public void init() {
		System.out.println("hi. I just initialized a new RequestMapping instance! Yay!");
		this.rm = LegacyRequestMapping.getInstance();
		rm.initialize();

		ahm = new AnnotationHandlerMapping("core.mvc.controller");
		ahm.initialize();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		RequestLine requestedLine = generateRequestLine(req);
		LegacyControllerInterface controller = rm.getController(requestedLine);
		try {
		ModelAndView mav = annotationBasedControllerDispatcher(req, res);
		mav.getView().render(mav.getModel(), req, res);
		
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
		
		
	}

	private RequestLine generateRequestLine(HttpServletRequest req) {
		return new RequestLine(req.getRequestURI(), RequestMethod.valueOf(req.getMethod()));
	}

	private ModelAndView annotationBasedControllerDispatcher(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.err.println("someone called annotation-based controllers!");
		return this.ahm.getHandler(req).handle(req, res);

	}

}
